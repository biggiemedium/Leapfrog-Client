package dev.leap.frog.Util.Render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class Drawutil {

    public static void releaseGL() {
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }

    // static classes below this line

    public static class Rect {
        private String tag;

        private int x;
        private int y;

        private int width;
        private int height;

        public Rect(String tag, int width, int height) {
            this.tag    = tag;
            this.width  = width;
            this.height = height;
        }

        public void transform(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void transform(int x, int y, int width, int height) {
            this.x      = x;
            this.y      = y;
            this.width  = width;
            this.height = height;
        }

        public boolean event_mouse(int mx, int my) {
            if (mx >= get_x() && my >= get_y() && mx <= get_screen_width() && my <= get_screen_height()) {
                return true;
            }

            return false;
        }

        public String get_tag() {
            return this.tag;
        }

        public int get_x() {
            return this.x;
        }

        public int get_y() {
            return this.y;
        }

        public int get_width() {
            return this.width;
        }

        public int get_height() {
            return this.height;
        }

        public int get_screen_width() {
            return ((int) this.x + this.width);
        }

        public int get_screen_height() {
            return ((int) this.y + this.height);
        }
    }

    public static class GL {
        public static void color(float r, float g, float b, float a) {
            GL11.glColor4f(r / 255, g / 255, b / 255, a / 255);
        }

        public static void start() {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }

        public static void finish() {
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        }

        public static void draw_rect(int x, int y, int width, int height) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glBegin(GL11.GL_QUADS); {
                GL11.glVertex2d(x + width, y);
                GL11.glVertex2d(x, y);
                GL11.glVertex2d(x, y + height);
                GL11.glVertex2d(x + width, y + height);
            }

            GL11.glEnd();
        }

        public static void resize(int x, int y, float size) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glTranslatef(x, y, 0);
            GL11.glScalef(size, size, 1);
            GL11.glColor4f(1, 1, 1, 1);
        }

        public static void resize(int x, int y, float size, String tag) {
            GL11.glScalef(1f / size, 1f / size, 1);
            GL11.glTranslatef(- x, - y, 0);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
    }

    public static class Turok {
        private String tag;

        //private Font font_manager;

        public Turok(String tag) {
            this.tag = tag;
        }

        public void resize(int x, int y, float size) {
            GL.resize(x, y, size);
        }

        public void resize(int x, int y, float size, String tag) {
            GL.resize(x, y, size, "end");
        }

        //public Font get_font_manager() {
        //     return this.font_manager;
        // }
    }

}
