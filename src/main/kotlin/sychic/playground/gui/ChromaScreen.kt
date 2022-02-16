package sychic.playground.gui

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.UIBlock
import gg.essential.universal.UMatrixStack
import gg.essential.universal.UResolution
import gg.essential.universal.shader.BlendState
import gg.essential.universal.shader.FloatUniform
import gg.essential.universal.shader.IntUniform
import gg.essential.universal.shader.UShader
import java.awt.Color

class ChromaScreen : WindowScreen(ElementaVersion.V1) {

    private val vertShader = """
        #version 120

        varying vec2 texcoord;

        void main() {
            gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
        
            texcoord = gl_Position.st;
        }
    """.trimIndent()

    private val fragShader = """
        #version 120
        uniform int u_time;

        varying vec2 texcoord;
        
        vec3 hsb2rgb( vec3 c ){
            vec3 rgb = clamp(abs(mod(c.x*6.0+vec3(0.0,4.0,2.0),
                             6.0)-3.0)-1.0,
                     0.0,
                     1.0 );
            rgb = rgb*rgb*(3.0-2.0*rgb);
            return c.z * mix(vec3(1.0), rgb, c.y);
        }

        void main() {
        
            float c = mod((2.0 * texcoord.x - texcoord.y) / 10.0, 1.0);//clamp(, 0.0, 1.0);
            
            vec3 rgb = hsb2rgb(vec3(mod(c - (u_time / 2000.0), 1.0), 0.7, 0.9));

            vec4 color = vec4(rgb.xyz, 1.0);
            
            gl_FragColor = color;
        }
    """.trimIndent()

    override fun afterInitialization() {
        initShaders()
    }

    private lateinit var shader: UShader
    private var timeUniform: IntUniform? = null

    private fun initShaders() {
        if (::shader.isInitialized)
            return

        shader = UShader.fromLegacyShader(vertShader, fragShader, BlendState.NORMAL)
        if (!shader.usable) {
            println("uh oh shader brokey")
            return
        }
        timeUniform = shader.getIntUniformOrNull("u_time")
    }

    override fun onDrawScreen(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.onDrawScreen(matrixStack, mouseX, mouseY, partialTicks)
        if (!::shader.isInitialized) return

        shader.bind()

        timeUniform?.setValue((System.currentTimeMillis() % 2000).toInt())

        UIBlock.drawBlockWithActiveShader(matrixStack, Color.WHITE, 00.0, 00.0, UResolution.windowWidth.toDouble(), UResolution.windowHeight.toDouble())

        shader.unbind()
    }
}