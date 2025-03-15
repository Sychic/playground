package sychic.playground.gui

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.UIBlock
import gg.essential.universal.UMatrixStack
import gg.essential.universal.UMinecraft
import gg.essential.universal.UResolution
import gg.essential.universal.shader.*
import java.awt.Color

class BlurScreen : WindowScreen(ElementaVersion.V1) {

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
        varying vec2 texcoord;
        uniform sampler2D txr;          // texture to blur
        uniform float r;                // blur radius
        void main() {
            float x,y,xx,yy,rr=r*r,dx,dy,w,w0;
            w0=0.3780/pow(r,1.975);
            vec2 p;
            vec4 col=vec4(0.0,0.0,0.0,0.0);
            for (dx=1.0/resolution.x,x=-r,p.x=0.5+(texcoord.x*0.5)+(x*dx);x<=r;x++,p.x+=dx){ 
                xx=x*x;
                for (dy=1.0/resolution.y,y=-r,p.y=0.5+(texcoord.y*0.5)+(y*dy);y<=r;y++,p.y+=dy) {
                    yy=y*y;
                    if (xx+yy<=rr) {
                        w=w0*exp((-xx-yy)/(2.0*rr));
                        col+=texture2D(txr,p)*w;
                    }
                }
            }
            gl_FragColor=col;
        }
    """.trimIndent()

    override fun afterInitialization() {
        initShaders()
    }

    private lateinit var shader: UShader
    private var radius: FloatUniform? = null
    private var sampler: SamplerUniform? = null

    private fun initShaders() {
        if (::shader.isInitialized)
            return

        shader = UShader.fromLegacyShader(vertShader, fragShader, BlendState.NORMAL)
        if (!shader.usable) {
            println("uh oh shader brokey")
            return
        }
        radius = shader.getFloatUniformOrNull("r")
        sampler = shader.getSamplerUniform("txr")
    }

    override fun onDrawScreen(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
//        super.onDrawScreen(matrixStack, mouseX, mouseY, partialTicks)
        if (!::shader.isInitialized) return

        radius?.setValue(5f)
        sampler?.setValue(UMinecraft.getMinecraft().framebuffer.fbo)

        shader.bind()

        UIBlock.drawBlockWithActiveShader(matrixStack, Color.WHITE, 00.0, 00.0, UResolution.windowWidth.toDouble(), UResolution.windowHeight.toDouble())

        shader.unbind()
    }
}