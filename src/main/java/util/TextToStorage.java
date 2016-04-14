// TextToStorage: converts text to speech then stores audio to object storage

package util;

import connectors.TexttoSpeechConnector;
import connectors.ObjectStorageConnector;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import java.awt.Component;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

import java.util.Random;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.util.ResponseUtil;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import javax.servlet.annotation.WebServlet;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.common.Payloads;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class TextToStorage {

    private TextToSpeech t2s;
    private ObjectStorageConnector objStorConnect;
    public static final String beginStr = "Here are the candidates for ";

    public TextToStorage() {
        TexttoSpeechConnector textConnector = new TexttoSpeechConnector();

        objStorConnect = new ObjectStorageConnector();
        objStorConnect.createContainer("candidates");

        t2s = new TextToSpeech();
        t2s.setUsernameAndPassword(textConnector.getUsername(),textConnector.getPassword());
    }

    public void putTextToStorage(String text, String filename) {
        Payload audioData = null;

        if ( text == null || text.length() == 0 ){
            System.out.println("Error occurred.");
            return;
        }

        InputStream speech = t2s.synthesize(text, "audio/wav");
        audioData = Payloads.create(speech);

        objStorConnect.uploadFile("candidates", filename+".wav", audioData);
    }
}
