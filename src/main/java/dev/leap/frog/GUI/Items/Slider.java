package dev.leap.frog.GUI.Items;

import dev.leap.frog.Module.Module;

public class Slider {
   private int x;
   private int y;

   private int width;
   private int height;

   private float value;
   private String name;
   private ModuleButton button;

   public Slider(int x, int y, int width, int height, float value, String name, ModuleButton button){
       this.x = x + 30;
       this.y = y;
       this.width = width;
       this.height = height;
       this.name = name;
       this.button = button;
   }

   public void Render(){

   }
   
    public void OnClick(int x, int y, int button, Module.Type type){

    }

}
