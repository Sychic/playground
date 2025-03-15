package sychic.playground.gui

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.universal.UGraphics
import gg.essential.universal.UMatrixStack
import gg.essential.universal.UMinecraft
import gg.essential.universal.UResolution
import gg.essential.universal.shader.BlendState
import gg.essential.universal.shader.UShader
import net.minecraft.client.gl.Framebuffer
import net.minecraft.client.gl.WindowFramebuffer

class ShaderTextScreen : WindowScreen(ElementaVersion.V1) {

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
        uniform vec2 resolution;
        uniform sampler2D backbuffer;

        void main() {
            vec2 pixel = vec2(1.0, 1.0) / resolution.xy;
            vec2 position = ( texcoord.xy / resolution.xy ) - pixel;
            
            vec4 color = texture2D(backbuffer, position);
            
            if (color.a > 0.0) {
                gl_FragColor = vec4(1.0, 1.0, 0.0, 0.5);
            }
        }
    """.trimIndent()

    override fun afterInitialization() {
        init()
    }

    private lateinit var shader: UShader
    private var framebuffer: Framebuffer? = null
//    private var timeUniform: IntUniform? = null

    private fun initShader() {
        if (::shader.isInitialized)
            return

        shader = UShader.fromLegacyShader(vertShader, fragShader, BlendState.NORMAL)
        if (!shader.usable) {
            println("uh oh shader brokey")
            return
        }

        framebuffer = WindowFramebuffer(UResolution.viewportWidth, UResolution.viewportHeight)
    }

    override fun onDrawScreen(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.onDrawScreen(matrixStack, mouseX, mouseY, partialTicks)
        if (!::shader.isInitialized) return

//        shader.bind()

//        timeUniform?.setValue((System.currentTimeMillis() % 2000).toInt())

//        UIBlock.drawBlockWithActiveShader(matrixStack, Color.WHITE, 00.0, 00.0, UResolution.windowWidth.toDouble(), UResolution.windowHeight.toDouble())
        framebuffer?.let { fb ->
            UMinecraft.getMinecraft().framebuffer.endWrite()
            fb.beginWrite(true)
            UGraphics.drawString(UMatrixStack(), "Hello World", 10f, 10f, 0x7f000000, false)
            fb.endWrite()
            UMinecraft.getMinecraft().framebuffer.beginWrite(true)
            fb.draw(fb.viewportWidth, fb.viewportHeight, false)
        }


//        shader.unbind()
    }
}