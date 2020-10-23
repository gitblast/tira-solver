package solver.ui;

import java.util.Arrays;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import solver.datastruct.InfoSetMap;
import solver.domain.CFRTrainer;
import solver.domain.Game;
import solver.domain.InfoSet;
import solver.domain.KuhnPoker;
import solver.domain.Rules;
import solver.domain.Trainer;

public class GUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {        
        HBox topBtns = new HBox();
        Button rm = new Button("RM");
        Button cfr = new Button("CFR");
        
        
        
        topBtns.setSpacing(5);
        topBtns.setPadding(new Insets(10, 10, 10, 10));
        
        topBtns.getChildren().addAll(rm, cfr);
        
        BorderPane root = new BorderPane();
        
        root.setTop(topBtns);
        
        //
        
        
        
        root.setCenter(rmContent());
        
        cfr.setOnAction((event) -> root.setCenter(cfrContent()));
        rm.setOnAction((event) -> root.setCenter(rmContent()));
        
        //
        
        Scene scene = new Scene(root, 1500, 800);
        
        primaryStage.setTitle("Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public VBox cfrContent() {
        VBox box = new VBox();
        
        Game kuhn = new KuhnPoker();
        
        CFRTrainer trainer = new CFRTrainer(kuhn);
        
        NumberAxis xAkseli = new NumberAxis();
        NumberAxis yAkseli = new NumberAxis(0, 100, 1);
        
        xAkseli.setLabel("Iteraatiot");
        yAkseli.setLabel("%");
        
        LineChart<Number, Number> chart1 = new LineChart<>(xAkseli, yAkseli);
        chart1.setCreateSymbols(false);
        chart1.setTitle("Pelaaja 1");
        
        XYChart.Series jack1 = new XYChart.Series();
        jack1.setName("Jätkä BET");
        
        XYChart.Series queen1 = new XYChart.Series();
        queen1.setName("Rouva BET");
        
        XYChart.Series king1 = new XYChart.Series();
        king1.setName("Kunkku BET");
        
        XYChart.Series jack1call = new XYChart.Series();
        jack1call.setName("Jätkä CALL vs BET");
        
        XYChart.Series queen1call = new XYChart.Series();
        queen1call.setName("Rouva CALL vs BET");
        
        XYChart.Series king1call = new XYChart.Series();
        king1call.setName("Kunkku CALL vs BET");
        
        chart1.getData().addAll(jack1, queen1, king1, jack1call, queen1call, king1call);
        
        //
        
        NumberAxis xAkseli2 = new NumberAxis();
        NumberAxis yAkseli2 = new NumberAxis(0, 100, 1);
        
        xAkseli2.setLabel("Iteraatiot");
        yAkseli2.setLabel("%");
        
        LineChart<Number, Number> chart2 = new LineChart<>(xAkseli2, yAkseli2);
        chart2.setCreateSymbols(false);
        chart2.setTitle("Pelaaja 2");
        
        XYChart.Series jack2 = new XYChart.Series();
        jack2.setName("Jätkä CALL vs BET");
        
        XYChart.Series queen2 = new XYChart.Series();
        queen2.setName("Rouva CALL vs BET");
        
        XYChart.Series king2 = new XYChart.Series();
        king2.setName("Kunkku CALL vs BET");
        
        XYChart.Series jack2check = new XYChart.Series();
        jack2check.setName("Jätkä BET vs CHECK");
        
        XYChart.Series queen2check = new XYChart.Series();
        queen2check.setName("Rouva BET vs CHECK");
        
        XYChart.Series king2check = new XYChart.Series();
        king2check.setName("Kunkku BET vs CHECK");
        
        chart2.getData().addAll(jack2, queen2, king2, jack2check, queen2check, king2check);
        
        //
        
        Label rounds = new Label("Treenattuja kierroksia: 0");
        
        //
        
        
        VBox stats1 = new VBox();
        VBox stats2 = new VBox();
        
        stats1.setPadding(new Insets(0, 0, 0, 10));
        
        Label strat1 = new Label("Strategia:");
        strat1.setPadding(new Insets(10, 0, 10, 0));
        
        Label ev1 = new Label("Odotusarvo: -");
        ev1.setPadding(new Insets(10, 0, 10, 0));
        
        Label jack1l = new Label("J BET: -");
        Label queen1l = new Label("Q BET: -");
        Label king1l = new Label("K BET: -");
        Label jack1l2 = new Label("J CALLvsBET: -");
        Label queen1l2 = new Label("Q CALLvsBET: -");
        Label king1l2 = new Label("K CALLvsBET: -");
        
        stats1.getChildren().addAll(strat1, jack1l, queen1l, king1l, jack1l2, queen1l2, king1l2, ev1);
        
        Label strat2 = new Label("Strategia:");
        strat2.setPadding(new Insets(10, 0, 10, 0));
        
        Label ev2 = new Label("Odotusarvo: -");
        ev2.setPadding(new Insets(10, 0, 10, 0));
        
        Label jack2l = new Label("J CALLvsBET: -");
        Label queen2l = new Label("Q CALLvsBET: -");
        Label king2l = new Label("K CALLvsBET: -");
        Label jack2l2 = new Label("J BETvsCHECK: -");
        Label queen2l2 = new Label("Q BETvsCHECK: -");
        Label king2l2 = new Label("K BETvsCHECK: -");
        
        stats2.getChildren().addAll(strat2, jack2l, queen2l, king2l, jack2l2, queen2l2, king2l2, ev2);
        
        //
        
        HBox trainBox = new HBox();
        trainBox.setSpacing(5);
        TextField trainField = new TextField();
        trainField.setPromptText("Kierrokset");
        Button trainBtn = new Button("Treenaa");
        
        trainBox.getChildren().addAll(trainField, trainBtn);
        
        
        trainBtn.setOnAction((event) -> {
            try {
                String text = trainField.getText();
                
                if (text.equals("")) return;
                
                int iterations = Integer.valueOf(text);
                
                int index = 0;
                
                int addition = 1;
                
                if (iterations > 100) {
                    addition = 10;
                } else if (iterations > 1000) {
                    addition = 100;
                } else if (iterations > 10000) {
                    addition = 1000;
                } else if (iterations > 100000) {
                    addition = 10000;
                }
                
                while (index < iterations) {

                    trainer.train(addition);
                    
                    double player1val = 1.0 * Math.round(trainer.getTotalValue() / trainer.getTrainIterations() * 10000) / 10000;
                    
                    ev1.setText("Odotusarvo: " + player1val);
                    ev2.setText("Odotusarvo: " + -1*player1val);

                    InfoSetMap map = trainer.getInfoSets();

                    InfoSet jack = map.get("J");
                    InfoSet queen = map.get("Q");
                    InfoSet king = map.get("K");
                    InfoSet jCall = map.get("JCB");
                    InfoSet qCall = map.get("QCB");
                    InfoSet kCall = map.get("KCB");



                    if (jack != null) {                            
                        jack1.getData().add(new XYChart.Data(trainer.getTrainIterations(), jack.getOptimalStrategy()[0] * 100));
                        
                
                        jack1l.setText("J BET: " + 1.0*Math.round(jack.getOptimalStrategy()[0]*10000)/100 + "%");
                    }

                    if (queen != null) {                            
                        queen1.getData().add(new XYChart.Data(trainer.getTrainIterations(), queen.getOptimalStrategy()[0] * 100));
                        
                        queen1l.setText("Q BET: " + 1.0*Math.round(queen.getOptimalStrategy()[0]*10000)/100 + "%");
                    }

                    if (king != null) {                            
                        king1.getData().add(new XYChart.Data(trainer.getTrainIterations(), king.getOptimalStrategy()[0] * 100));
                        
                        king1l.setText("K BET: " + 1.0*Math.round(king.getOptimalStrategy()[0]*10000)/100 + "%");
                    }

                    if (jCall != null) {
                        jack1call.getData().add(new XYChart.Data(trainer.getTrainIterations(), jCall.getOptimalStrategy()[0] * 100));
                        
                        jack1l2.setText("J CALLvsBET: " + 1.0*Math.round(jCall.getOptimalStrategy()[0]*10000)/100 + "%");
                    }

                    if (qCall != null) {
                        queen1call.getData().add(new XYChart.Data(trainer.getTrainIterations(), qCall.getOptimalStrategy()[0] * 100));
                        
                        queen1l2.setText("Q CALLvsBET: " + 1.0*Math.round(qCall.getOptimalStrategy()[0]*10000)/100 + "%");
                    }

                    if (kCall != null) {
                        king1call.getData().add(new XYChart.Data(trainer.getTrainIterations(), kCall.getOptimalStrategy()[0] * 100));
                        
                        king1l2.setText("K CALLvsBET: " + 1.0*Math.round(kCall.getOptimalStrategy()[0]*10000)/100 + "%");
                    }
                    
                    // player 2
                    
                    
                    InfoSet jVbet = map.get("JB");
                    InfoSet qVbet = map.get("QB");
                    InfoSet kVbet = map.get("KB");
                    InfoSet jVcheck = map.get("JC");
                    InfoSet qVcheck = map.get("QC");
                    InfoSet kVcheck = map.get("KC");
                    
                    if (jVbet != null) {                            
                        jack2.getData().add(new XYChart.Data(trainer.getTrainIterations(), jVbet.getOptimalStrategy()[0] * 100));
                        
                
                        jack2l.setText("J CALLvsBET: " + 1.0*Math.round(jVbet.getOptimalStrategy()[0]*10000)/100 + "%");
                    }
                    
                    if (qVbet != null) {                            
                        queen2.getData().add(new XYChart.Data(trainer.getTrainIterations(), qVbet.getOptimalStrategy()[0] * 100));
                        
                
                        queen2l.setText("Q CALLvsBET: " + 1.0*Math.round(qVbet.getOptimalStrategy()[0]*10000)/100 + "%");
                    }
                    
                    if (kVbet != null) {                            
                        king2.getData().add(new XYChart.Data(trainer.getTrainIterations(), kVbet.getOptimalStrategy()[0] * 100));
                        
                
                        king2l.setText("K CALLvsBET: " + 1.0*Math.round(kVbet.getOptimalStrategy()[0]*10000)/100 + "%");
                    }
                    
                    if (jVcheck != null) {                            
                        jack2check.getData().add(new XYChart.Data(trainer.getTrainIterations(), jVcheck.getOptimalStrategy()[0] * 100));
                        
                
                        jack2l2.setText("J BETvsCHECK: " + 1.0*Math.round(jVcheck.getOptimalStrategy()[0]*10000)/100 + "%");
                    }
                    
                    if (qVcheck != null) {                            
                        queen2check.getData().add(new XYChart.Data(trainer.getTrainIterations(), qVcheck.getOptimalStrategy()[0] * 100));
                        
                
                        queen2l2.setText("Q BETvsCHECK: " + 1.0*Math.round(qVcheck.getOptimalStrategy()[0]*10000)/100 + "%");
                    }
                    
                    if (kVcheck != null) {                            
                        king2check.getData().add(new XYChart.Data(trainer.getTrainIterations(), kVcheck.getOptimalStrategy()[0] * 100));
                        
                
                        king2l2.setText("K BETvsCHECK: " + 1.0*Math.round(kVcheck.getOptimalStrategy()[0]*10000)/100 + "%");
                    }


                    index += addition;
                    
                } 
                
                
                rounds.setText("Treenattuja kierroksia: " + trainer.getTrainIterations());
                
                
            } catch (Error e) {
                System.out.println("Error training");
            }
        });
        
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        
        
        vbox.getChildren().addAll(rounds, trainBox);
        
        
        
        
        //
        HBox hbox1 = new HBox(stats1, chart1);
        HBox hbox2 = new HBox(stats2, chart2);
        
        HBox.setHgrow(hbox1, Priority.ALWAYS);
        HBox.setHgrow(hbox2, Priority.ALWAYS);
        
        HBox chartBox = new HBox(hbox1, hbox2);
        
        VBox.setVgrow(chartBox, Priority.ALWAYS);
        
        
        HBox.setHgrow(chart1, Priority.ALWAYS);
        HBox.setHgrow(chart2, Priority.ALWAYS);
        
        box.getChildren().addAll(vbox, chartBox);
        
        return box;
    }
    
    public HBox rmContent() {
        HBox box = new HBox();
        
        int[][] rockPaperScissorMap = {
            {0, -1, 1},
            {1, 0, -1},
            {-1, 1, 0}
        };
        
        String[] rockPaperScissorLabels = { "Kivi", "Paperi", "Sakset" };
        
        
        Rules rps = new Rules("Kivi-Paperi-Sakset", rockPaperScissorMap, rockPaperScissorLabels);
        
        Trainer trainer = new Trainer(rps);
        
        NumberAxis xAkseli = new NumberAxis();
        NumberAxis yAkseli = new NumberAxis(0, 100, 1);
        
        xAkseli.setLabel("Iteraatiot");
        yAkseli.setLabel("%");
        
        LineChart<Number, Number> chart = new LineChart<>(xAkseli, yAkseli);
        chart.setCreateSymbols(false);
        chart.setTitle("Kivi-Paperi-Sakset");
        
        XYChart.Series rock = new XYChart.Series();
        rock.setName("Kivi");
        
        XYChart.Series paper = new XYChart.Series();
        paper.setName("Paperi");
        
        XYChart.Series scissors = new XYChart.Series();
        scissors.setName("Sakset");
        
        
        
        rock.getData().add(new XYChart.Data(0, 100/3));
        paper.getData().add(new XYChart.Data(0, 100/3));
        scissors.getData().add(new XYChart.Data(0, 100/3));
        
        chart.getData().add(rock);
        chart.getData().add(paper);
        chart.getData().add(scissors);
        
        //
        
        Label strats = new Label("Strategia:");
        
        strats.setPadding(new Insets(10, 0, 10, 0));
        
        Label kivi = new Label("Kivi: -");
        Label paperi = new Label("Paperi: -");
        Label sax = new Label("Sakset: -");
        
        //
        
        Label rounds = new Label("Treenattuja kierroksia: 0");
        
        //
        
        HBox trainBox = new HBox();
        trainBox.setSpacing(5);
        TextField trainField = new TextField();
        trainField.setPromptText("Kierrokset");
        Button trainBtn = new Button("Treenaa");
        
        trainBox.getChildren().addAll(trainField, trainBtn);
        
        trainBtn.setOnAction((event) -> {
            try {
                String text = trainField.getText();
                
                if (text.equals("")) return;
                
                int iterations = Integer.valueOf(text);
                
                if (iterations < 10) {
                    for (int i = 0; i < iterations; i++) {
                    
                        trainer.train(1);
                        double[] optimal =  trainer.getOptimalStrategy();
                        
                        kivi.setText("Kivi: " + optimal[0]*100 + "%" );
                        paperi.setText("Paperi: " + optimal[1]*100 + "%" );
                        sax.setText("Sakset: " + optimal[2]*100 + "%" );
                        
                        
                        rock.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[0] * 100));
                        paper.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[1] * 100));
                        scissors.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[2] * 100));
                    }
                } else if (iterations < 1000) {
                    for (int i = 0; i < iterations; i += 10) {
                    
                        trainer.train(10);
                        double[] optimal =  trainer.getOptimalStrategy();
                        
                        kivi.setText("Kivi: " + optimal[0]*100 + "%" );
                        paperi.setText("Paperi: " + optimal[1]*100 + "%" );
                        sax.setText("Sakset: " + optimal[2]*100 + "%" );
                        

                        rock.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[0] * 100));
                        paper.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[1] * 100));
                        scissors.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[2] * 100));
                    }
                } else {
                    for (int i = 0; i < iterations; i += 100) {
                    
                        trainer.train(100);
                        double[] optimal =  trainer.getOptimalStrategy();
                        
                        kivi.setText("Kivi: " + optimal[0]*100 + "%" );
                        paperi.setText("Paperi: " + optimal[1]*100 + "%" );
                        sax.setText("Sakset: " + optimal[2]*100 + "%" );
                        

                        rock.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[0] * 100));
                        paper.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[1] * 100));
                        scissors.getData().add(new XYChart.Data(trainer.getTrainIterations(), optimal[2] * 100));
                    }
                }
                
                rounds.setText("Treenattuja kierroksia: " + trainer.getTrainIterations());
                
            } catch (Error e) {
                System.out.println("Error training");
            }
        });
        
        
        
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        
        
        vbox.getChildren().addAll(rounds, trainBox, strats, kivi, paperi, sax);
        
        box.getChildren().addAll(vbox, chart);
        
        HBox.setHgrow(chart, Priority.ALWAYS);
        
        return box;
    }
    
}


