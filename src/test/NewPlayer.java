/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Md-Zebronic
 */
public class NewPlayer extends Application {
    
    static  int w,h;
    BorderPane root;
    MediaPlayer mp;
    Media media;
    MediaView mv;
    Duration duration;
    FileChooser fileChooser;
    MenuBar menu;
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException  {
        
       
     
        File file1=new File("src/test/gof.mp4");
        media=new Media(file1.toURI().toString());
        mp=new MediaPlayer(media);
        mv=new MediaView(mp);
        mp.setAutoPlay(false);
        
        MenuItem open=new MenuItem("Open");
        Menu file=new Menu("File");
        MenuItem fast=new MenuItem("Fast(x1.5)");
        MenuItem normal=new MenuItem("Normal");
        MenuItem slow=new MenuItem("Slow(x0.5)");
        
        Menu playBack=new Menu("Playback");
        Menu Go=new Menu("Go Online");
        MenuItem go=new MenuItem("Our Website");
        file.getItems().add(open);
        playBack.getItems().add(fast);
        playBack.getItems().add(normal);
        playBack.getItems().add(slow);
        Go.getItems().add(go);
        menu=new MenuBar();
        menu.getMenus().add(file);
        menu.getMenus().add(playBack);
        menu.getMenus().add(Go);
        menu.setStyle("-fx-background-color:darkgray;");
        
       Image image=new Image(getClass().getResourceAsStream("bg.png"));
       ImageView imageView=new ImageView(image);
       imageView.setFitHeight(550); 
       imageView.setFitWidth(960); 
      
       root = new BorderPane();
       
       root.setStyle("-fx-background-color:lightgray;");
       root.setTop(menu);
       root.setBottom(addToolBar());
       root.setCenter(imageView);
       Scene scene = new Scene(root, 980, 620);
        primaryStage.setTitle("Team Player");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("bg1.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    
       fast.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
             mp.setRate(1.5);
           }
        });
        normal.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
             mp.setRate(1);
           }
        });
       slow.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
             mp.setRate(0.5);
           }
        });
     go.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent e) {
          Desktop desktop = null;
             if (Desktop.isDesktopSupported()) 
             {
             try
             {
                 desktop = Desktop.getDesktop();
                 File fileToOpen=new File("src/website/Media.player.html");
                 desktop.open(fileToOpen);
             } catch (IOException ex)
             {
                
             }
          }
        }
    });
            
       
        fileChooser=new FileChooser();
        FileChooser.ExtensionFilter fileExtensions= 
        new FileChooser.ExtensionFilter("Video & Audio Supported","*mp4","*3gp","*mp3","*aac","*m4a");
        fileChooser.getExtensionFilters().add(fileExtensions);
        open.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
               File file=fileChooser.showOpenDialog(primaryStage);
               mp.pause();
               if(file!=null){
                   try{
                    
                       media=new Media(file.toURI().toURL().toExternalForm());
                       mp=new MediaPlayer(media);
                       mp.setOnReady(() -> primaryStage.sizeToScene());
                       mp.setAutoPlay(true);
                       mv=new MediaView(mp);
                       root.getChildren().add(mv);
                       mv.fitWidthProperty().bind(root.widthProperty());
                       mv.fitHeightProperty().bind(root.heightProperty());
                       mv.setPreserveRatio(true);
                       root.setStyle("-fx-background-color:black;");
                       root.setTop(menu);
                       root.setCenter(mv);
                       root.setBottom(addToolBar());
                       Scene scene = new Scene(root);
                       primaryStage.setScene(scene);
                       primaryStage.show();
                   }catch(MalformedURLException e1){
                   }
               }
           }
        });
      
     
       
       
    }   

    private HBox addToolBar() {
    HBox toolBar = new HBox();
    toolBar.setPadding(new Insets(10));
    toolBar.setAlignment(Pos.CENTER);
    toolBar.alignmentProperty().isBound();
    toolBar.setSpacing(8);
    toolBar.setStyle("-fx-background-color:darkgray;");
    Button firstButton = new Button("|<");
    Button backButton = new Button("<<");
    Button pauseButton = new Button("||");
    Button forwardButton = new Button(">>");
    Button lastButton = new Button("|>");
    Button reloadButton = new Button("@");
    Button filesButton = new Button("|=|");
    Slider vol = new Slider();vol.setValue(100);vol.setPrefWidth(50);
    Slider time = new Slider();time.setPrefWidth(300);
    Label volume = new Label("Volume :");
    Label playTime =new Label("PlayTime :");
    toolBar.getChildren().addAll( filesButton,firstButton, backButton, pauseButton, forwardButton, lastButton, reloadButton,playTime, time, volume,vol);
pauseButton.setOnAction(new EventHandler<ActionEvent>(){
         @Override
         public void handle(ActionEvent e){
             MediaPlayer.Status status =mp.getStatus();
             
             if(status==MediaPlayer.Status.PLAYING){
                 if(mp.getCurrentTime().greaterThanOrEqualTo(mp.getTotalDuration())){
                 mp.seek(mp.getStartTime());
                 mp.play();
             }
             else{
                 mp.pause();
                 pauseButton.setText(">");
                 }
             }
             if(status==MediaPlayer.Status.PAUSED || status==MediaPlayer.Status.HALTED || status==MediaPlayer.Status.STOPPED){
                 mp.play();
                 pauseButton.setText("||");
             }
         }
     });

forwardButton.setOnAction((ActionEvent e) -> {
mp.seek(mp.getCurrentTime().multiply(1.5));
});

backButton.setOnAction((ActionEvent e) -> {
mp.seek(mp.getCurrentTime().divide(1.5));
});

firstButton.setOnAction((ActionEvent e) -> {
mp.seek(mp.getStartTime());
mp.stop();
});

lastButton.setOnAction((ActionEvent e) -> {
mp.seek(mp.getTotalDuration());
mp.stop();
});

reloadButton.setOnAction((ActionEvent e) -> {
mp.seek(mp.getStartTime());
});

filesButton.setOnAction((ActionEvent e) -> {
                       FileChooser fc = new FileChooser();
                      FileChooser.ExtensionFilter fileExtensions= 
                      new FileChooser.ExtensionFilter("Video & Audio Supported","*mp4","*3gp","*mp3","*aac","*m4a");
                      Stage primaryStage=new Stage();
                      mp.pause();
                      File file=fileChooser.showOpenDialog(primaryStage);
                       if(file!=null){
                   try{
                       media=new Media(file.toURI().toURL().toExternalForm());
                       mp=new MediaPlayer(media);
                       mv=new MediaView(mp);
                       
                       mp.setAutoPlay(true);
                       mv.fitWidthProperty().bind(root.widthProperty());
                       mv.fitHeightProperty().bind(root.heightProperty());
                       mv.setPreserveRatio(true);
                       root.setStyle("-fx-background-color:black;");
                       root.setTop(menu);
                       root.setCenter(mv);
                       root.setBottom(addToolBar());
                       
                      Scene scene = new Scene(root);
                       primaryStage.setScene(scene);
                   }catch(MalformedURLException e1){
                   }}
});

mp.currentTimeProperty().addListener(new InvalidationListener(){
         @Override
         public void invalidated(Observable ov){
             updateValues();
         }

         private void updateValues() {
              Platform.runLater(new Runnable(){
           @Override
           public void run(){
               time.setValue(mp.getCurrentTime().toMillis()/mp.getTotalDuration().toMillis()*100);
           } 
        });
         }
         
     });
time.valueProperty().addListener(new InvalidationListener(){
        @Override
        public void invalidated(Observable ov){
            if(time.isPressed()){
                mp.seek(mp.getMedia().getDuration().multiply(time.getValue()/100));  
            }
        }
        
    });

vol.valueProperty().addListener(new InvalidationListener(){
        @Override
        public void invalidated(Observable ov){
          if(vol.isPressed()){
            mp.setVolume(vol.getValue()/100);
          }    
        }
     });

return toolBar; 
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}

