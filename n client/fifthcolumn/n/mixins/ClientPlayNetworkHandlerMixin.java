// 
// Decompiled by Procyon v0.5.36
// 

package fifthcolumn.n.mixins;

import net.minecraft.class_2613;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.class_2759;
import net.minecraft.class_634;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { class_634.class }, priority = 100)
public class ClientPlayNetworkHandlerMixin
{
    @Inject(method = { "onPlayerSpawnPosition" }, at = { @At("TAIL") })
    private void n$playerSpawnPositionEvent(final class_2759 packet, final CallbackInfo cinfo) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: new             new            !!! ERROR
        //     6: dup            
        //     7: aload_1         /* packet */
        //     8: invokevirtual   net/minecraft/class_2759.method_11870:()Lnet/minecraft/class_2338;
        //    11: invokespecial   invokespecial  !!! ERROR
        //    14: invokeinterface meteordevelopment/orbit/IEventBus.post:(Ljava/lang/Object;)Ljava/lang/Object;
        //    19: pop            
        //    20: return         
        //    MethodParameters:
        //  Name    Flags  
        //  ------  -----
        //  packet  
        //  cinfo   
        // 
        // The error that occurred was:
        // 
        // java.lang.reflect.GenericSignatureFormatError
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.error(SignatureParser.java:70)
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.parseFormalParameters(SignatureParser.java:416)
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.parseMethodTypeSignature(SignatureParser.java:407)
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.parseMethodSignature(SignatureParser.java:88)
        //     at com.strobel.assembler.metadata.MetadataParser.parseMethodSignature(MetadataParser.java:234)
        //     at com.strobel.assembler.metadata.MetadataParser.parseMethod(MetadataParser.java:166)
        //     at com.strobel.assembler.metadata.ClassFileReader$Scope.lookupMethod(ClassFileReader.java:1303)
        //     at com.strobel.assembler.metadata.ClassFileReader$Scope.lookupMethodHandle(ClassFileReader.java:1258)
        //     at com.strobel.assembler.metadata.ClassFileReader$Scope.lookup(ClassFileReader.java:1352)
        //     at com.strobel.assembler.ir.MetadataReader.readAttributeCore(MetadataReader.java:306)
        //     at com.strobel.assembler.metadata.ClassFileReader.readAttributeCore(ClassFileReader.java:261)
        //     at com.strobel.assembler.ir.MetadataReader.inflateAttributes(MetadataReader.java:439)
        //     at com.strobel.assembler.metadata.ClassFileReader.visitAttributes(ClassFileReader.java:1134)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:439)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:377)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveType(MetadataSystem.java:129)
        //     at com.strobel.decompiler.NoRetryMetadataSystem.resolveType(DecompilerDriver.java:476)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveCore(MetadataSystem.java:81)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:104)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:616)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:128)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:626)
        //     at com.strobel.assembler.metadata.MethodReference.resolve(MethodReference.java:177)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2438)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:655)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:365)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Inject(method = { "onPlayerSpawn" }, at = { @At("TAIL") })
    private void n$playerSpawnEvent(final class_2613 packet, final CallbackInfo ci) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: new             new            !!! ERROR
        //     6: dup            
        //     7: aload_1         /* packet */
        //     8: invokevirtual   net/minecraft/class_2613.method_11230:()Ljava/util/UUID;
        //    11: new             Lnet/minecraft/class_2338;
        //    14: dup            
        //    15: aload_1         /* packet */
        //    16: invokevirtual   net/minecraft/class_2613.method_11231:()D
        //    19: d2i            
        //    20: aload_1         /* packet */
        //    21: invokevirtual   net/minecraft/class_2613.method_11232:()D
        //    24: d2i            
        //    25: aload_1         /* packet */
        //    26: invokevirtual   net/minecraft/class_2613.method_11233:()D
        //    29: d2i            
        //    30: invokespecial   net/minecraft/class_2338.<init>:(III)V
        //    33: invokespecial   invokespecial  !!! ERROR
        //    36: invokeinterface meteordevelopment/orbit/IEventBus.post:(Ljava/lang/Object;)Ljava/lang/Object;
        //    41: pop            
        //    42: return         
        //    MethodParameters:
        //  Name    Flags  
        //  ------  -----
        //  packet  
        //  ci      
        // 
        // The error that occurred was:
        // 
        // java.lang.reflect.GenericSignatureFormatError
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.error(SignatureParser.java:70)
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.parseFormalParameters(SignatureParser.java:416)
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.parseMethodTypeSignature(SignatureParser.java:407)
        //     at com.strobel.assembler.metadata.signatures.SignatureParser.parseMethodSignature(SignatureParser.java:88)
        //     at com.strobel.assembler.metadata.MetadataParser.parseMethodSignature(MetadataParser.java:234)
        //     at com.strobel.assembler.metadata.MetadataParser.parseMethod(MetadataParser.java:166)
        //     at com.strobel.assembler.metadata.ClassFileReader$Scope.lookupMethod(ClassFileReader.java:1303)
        //     at com.strobel.assembler.metadata.ClassFileReader$Scope.lookupMethodHandle(ClassFileReader.java:1258)
        //     at com.strobel.assembler.metadata.ClassFileReader$Scope.lookup(ClassFileReader.java:1352)
        //     at com.strobel.assembler.ir.MetadataReader.readAttributeCore(MetadataReader.java:306)
        //     at com.strobel.assembler.metadata.ClassFileReader.readAttributeCore(ClassFileReader.java:261)
        //     at com.strobel.assembler.ir.MetadataReader.inflateAttributes(MetadataReader.java:439)
        //     at com.strobel.assembler.metadata.ClassFileReader.visitAttributes(ClassFileReader.java:1134)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:439)
        //     at com.strobel.assembler.metadata.ClassFileReader.readClass(ClassFileReader.java:377)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveType(MetadataSystem.java:129)
        //     at com.strobel.decompiler.NoRetryMetadataSystem.resolveType(DecompilerDriver.java:476)
        //     at com.strobel.assembler.metadata.MetadataSystem.resolveCore(MetadataSystem.java:81)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:104)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:616)
        //     at com.strobel.assembler.metadata.MetadataResolver.resolve(MetadataResolver.java:128)
        //     at com.strobel.assembler.metadata.CoreMetadataFactory$UnresolvedType.resolve(CoreMetadataFactory.java:626)
        //     at com.strobel.assembler.metadata.MethodReference.resolve(MethodReference.java:177)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferCall(TypeAnalysis.java:2438)
        //     at com.strobel.decompiler.ast.TypeAnalysis.doInferTypeForExpression(TypeAnalysis.java:1029)
        //     at com.strobel.decompiler.ast.TypeAnalysis.inferTypeForExpression(TypeAnalysis.java:803)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:672)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:655)
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:365)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:109)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
