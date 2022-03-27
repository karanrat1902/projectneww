package com.karanrat.linebot;

public abstract class Somtea {
   //abstac
   public abstract String checktext(String text);
 
   // field
   private String food;
   private String coffee;
   private String chanom;
   private String beakerry;
   public String getFood(){
      return this.food;
   }

   public void setFood(String text){
      this.food = text;
   }

   public String getCoffee(){
      return this.coffee;
   }

   public void setCoffee(String text){
      this.coffee = text;
   }

   public String getChanom(){
      return this.chanom;
   }

   public void setChanom(String text){
      this.chanom = text;
   }

   public String getBeakerry(){
      return this.beakerry;
   }

   public void setBeakerry(String text){
      this.beakerry = text;
   }

   public String getThankyou(){
      return "ขอบคุณที่ใช้บริการค่ะ❤️";
      
   }



}
