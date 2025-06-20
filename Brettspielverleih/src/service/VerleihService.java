package service;

import model.Verleihvorgang;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class VerleihService {
    public Object verleihStarten(Verleihvorgang verleihvorgang) {
    	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    	//    	LocalTime currentTime = LocalTime.now();	
//    	LocalDate currentDate = LocalDate.now();	
//    	LocalDateTime currentDateTime = LocalDateTime.now();
    	System.out.println(timeStamp);
		return timeStamp;
    }

    public void verleihBeenden(Verleihvorgang verleihvorgang) {
        // Verleihvorgang beenden
    }

    public List<Verleihvorgang> getÜberfälligeRückgaben() {
		return null;
        // Überfällige Rückgaben abrufen
    }
    
}

