package com.future.frouter.processor

import com.future.frouter.annotation.FRounter
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.snaky.frouter.data.IModuleRouter
import com.snaky.frouter.data.RouterData
import com.squareup.kotlinpoet.*
import kotlin.concurrent.thread


class FRouterProcessor(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {

    companion object {
        val CLASS_PREFIX = "_FRouter"
        val PACKAGE_NAME = "com.router"
    }

    private val fRouterName = FRounter::class.qualifiedName.toString()

    private lateinit var fRounterType: KSType //用来判断类型

    private val moduleField = "module"

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger("start")
        try {
            handleAnno(resolver)
        } catch (e: Exception) {
            logger(e?.cause?.message?:"出错了")
        }

        logger("end")
        return emptyList()
    }


    private fun handleAnno(resolver: Resolver) {
        initData(resolver)

        resolver.getSymbolsWithAnnotation(fRouterName)
            .filterIsInstance<KSClassDeclaration>()
            .forEach { ksClassDec ->
                buildFileSpec(ksClassDec)
            }
    }
    /** 初始化一些参数  **/
    private fun initData(resolver: Resolver) {
        fRounterType = resolver.getClassDeclarationByName(
            resolver.getKSNameFromString(fRouterName)
        )!!.asType(emptyList())
    }


    /**
     * 构建class
     */
    lateinit var superInterface: KSType
    private fun buildFileSpec(ksClassDec: KSClassDeclaration) {
        /** getRouterImpClass 实现 **/
        superInterface = ksClassDec
            .superTypes.map { it.resolve() }
            .filter { (it.declaration as KSClassDeclaration).classKind == ClassKind.INTERFACE }
            .iterator()
            .next()

        val classSimpName = (superInterface.declaration.simpleName.asString()) + CLASS_PREFIX
        val fileSpec = FileSpec.builder(superInterface.declaration.packageName.asString(), classSimpName)
            .addType(buildTypeSpec(ksClassDec, classSimpName))
            .build()

        val dependencies = Dependencies(true)
        thread(true) {
            environment.codeGenerator.createNewFile(
                dependencies = dependencies,
                packageName = PACKAGE_NAME,
                fileName = classSimpName,
                extensionName = "kt"
            ).bufferedWriter().use { writer ->
                try {
                    fileSpec.writeTo(writer)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    writer.flush()
                    writer.close()
                }
            }
        }
    }

    /**
     * 构建方法
     */
    private fun buildTypeSpec(ksClassDec: KSClassDeclaration, className: String): TypeSpec {

        return TypeSpec.classBuilder(className)
            .addSuperinterface(IModuleRouter::class)
            .addFunctions(buildFunSpec2(ksClassDec))
            .build()
    }

    /**
     * 构建方法
     */
    private val getIModuleRouter = "getIModuleRouter"

    private fun buildFunSpec2(ksClassDec: KSClassDeclaration): List<FunSpec> {
        val annotations =
            ksClassDec.annotations.find { it.annotationType.resolve() == fRounterType }
        val moduleValue = annotations?.arguments
            ?.find { it.name?.getShortName() == moduleField }
            ?.value?.toString() ?: ""


        val specList = mutableListOf<FunSpec>()

        val ksClassDecName = ClassName.bestGuess(ksClassDec.qualifiedName?.asString()?:"")
        val superInterfaceName = superInterface.declaration.qualifiedName?.asString()?:""

        specList.add(
            FunSpec.builder(getIModuleRouter)
                .addModifiers(KModifier.OVERRIDE)
                .returns(RouterData::class)
                .addStatement("return RouterData(%S, %T(), %S)",moduleValue, ksClassDecName, superInterfaceName)
                .build()
        )
        return specList
    }

    private fun logger(message: String) {
//        return
        environment.logger.info("process \t $message")
    }

}