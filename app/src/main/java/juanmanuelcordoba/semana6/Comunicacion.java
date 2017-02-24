package juanmanuelcordoba.semana6;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 * Created by estudiante on 24/02/17.
 */

public class Comunicacion extends Observable implements Runnable {

    private static Comunicacion com;
    public static final String android = "10.0.2.2";
    public static final String miIp = "228.7.8.3";
    private boolean corre;
    private boolean conec;
    private boolean reset;
    private DatagramSocket data;

    private Comunicacion() {
        corre = true;
        conec = true;
        reset = false;
    }

    public static Comunicacion getInstance() {
        if (com == null) {
            com = new Comunicacion();
            Thread hilo = new Thread(com);
            hilo.start();
        }
        return com;
    }

    public void run() {
        while (corre) {
            if (conec) {
                if (reset) {
                    if (data != null) {
                        data.close();
                    }
                    reset=false;
                }
                conec = !intento();
            }else
        }
    }

    public boolean intento() {
        try {
            data = new DatagramSocket();
            setChanged();
            notifyObservers();
            clearChanged();
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        }
    }
    private byte[] serialize(Object data) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            bytes = baos.toByteArray();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private Object deserialize(byte[] bytes) {
        Object data = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            data = ois.readObject();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }


    public DatagramPacket recibir() {
        byte[] bytes = new byte[1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        try {
            data.receive(packet);
            System.out.println("Data recived from"+ packet.getAddress()+ ":"+ packet.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void enviar(final String mensaje, final String destino, final int puerto){
        Thread (new Runnable(){
            public void run(){
                if (data != null){
                    try{
                        InetAddress net = InetAddress.getByAddress(destino);
                        byte[] data = mensaje.getBytes();
                        DatagramPacket packet = new DatagramPacket(data, data.length,net,puerto);
                        System.out.println("Data send to"+ net.getHostAddress() + ":"+ puerto);
                        System.out.println("Data was sent");
                    }catch (UnknownHostException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
