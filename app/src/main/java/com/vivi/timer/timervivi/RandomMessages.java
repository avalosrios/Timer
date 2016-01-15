package com.vivi.timer.timervivi;

import java.util.Random;

/**
 * Created by oavalos on 1/14/16.
 */
public class RandomMessages {

    private String [] messages;
    private Random random;

    public RandomMessages(){
        this(new String[]{"Te quiero","Vivi te falta poquito",
                "Besitos :-* \t :-*", "Hola Terroncito :-D", "Give it to me baby!",
                "Te llevo en el <3", "Trabaja o te doy mas besos :-*"});
    }

    public RandomMessages(String [] messages){
        this.messages = messages;
        this.random = new Random();
    }

    public String pickOne(){
        int randPos = this.random.nextInt(this.messages.length);
        return this.messages[randPos];
    }
}
