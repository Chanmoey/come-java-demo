package com.moon.tinylombok.aspect;

import com.moon.tinylombok.annotation.MyGetter;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author Chanmoey
 * @date 2022年05月31日
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.moon.tinylombok.annotation.MyGetter")
public class MyGetterAspect extends AbstractProcessor {

    private Messager messager;
    private JavacTrees trees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        // 编译器打印log。
        this.messager = processingEnv.getMessager();

        // 获取抽象语法树。
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();

        // 封装了创建抽象语法树节点的方法。
        this.treeMaker = TreeMaker.instance(context);

        // 提供创建标识符的方法
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // 获取所有被标注了MyGetter注解的类。
        Set<? extends Element> elementSet = roundEnv.getElementsAnnotatedWith(MyGetter.class);
        elementSet.forEach(e -> {

            // 生成JC语法树。
            JCTree tree = this.trees.getTree(e);
            tree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();

                    // 遍历抽象语法树中的所有变量。
                    for (JCTree jcTree : jcClassDecl.defs) {
                        if (jcTree.getKind().equals(Tree.Kind.VARIABLE)) {
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                            jcVariableDeclList.append(jcVariableDecl);
                        }
                    }

                    // 对变量生成对应的getter方法。
                    jcVariableDeclList.forEach(v -> {
                        messager.printMessage(Diagnostic.Kind.NOTE, v.getName() + "created the method ...");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(creatGetter(v));
                    });

                    super.visitClassDef(jcClassDecl);
                }
            });
        });

        return true;
    }

    private JCTree.JCMethodDecl creatGetter(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statementList = new ListBuffer<>();

        statementList.append(
                this.treeMaker.Return(
                        this.treeMaker.Select(
                                this.treeMaker.Ident(this.names.fromString("this")),
                                jcVariableDecl.getName()
                        )
                )
        );

        JCTree.JCBlock body = this.treeMaker.Block(0, statementList.toList());
        return this.treeMaker.MethodDef(
                this.treeMaker.Modifiers(Flags.PUBLIC),
                getNewMethodName(jcVariableDecl.getName()),
                jcVariableDecl.vartype,
                List.nil(),
                List.nil(),
                List.nil(),
                body,
                null
        );
    }

    private Name getNewMethodName(Name name) {
        String s = name.toString();
        return this.names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1, name.length()));
    }
}
