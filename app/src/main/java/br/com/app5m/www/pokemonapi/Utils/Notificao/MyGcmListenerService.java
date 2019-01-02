package br.com.app5m.www.pokemonapi.Utils.Notificao;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Random;

import br.com.app5m.www.pokemonapi.R;
import br.com.app5m.www.pokemonapi.Telas.Lista;

public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, final Bundle data) {

        String id_logado = data.getString("id_logado");

         //EXEMPLO DE NOTIFICAÇÃO

        if (id_logado.equals("id_logado")){
            String subtitle = data.getString("subtitle");

            Intent aint = new Intent(getApplicationContext(), Lista.class);

            PendingIntent p = PendingIntent.getActivity(getApplicationContext(), 1, aint, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder mBuilder = new Notification.Builder(getApplicationContext());
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)

                    .setContentTitle("Título")
                    .setStyle(new Notification.BigTextStyle().setBigContentTitle("Título Content"))
                    .setContentText(subtitle)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setContentIntent(p)
                    .setAutoCancel(true).setVibrate(new long[]{150, 300});

            int m = new Random().nextInt(9999 - 1000) + 1000;
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = notificationManager.getNotificationChannel("teste");
                if (notificationChannel == null) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    notificationChannel = new NotificationChannel("id_channel", "teste", importance);
                    //notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                    mBuilder.setChannelId("id_channel");
                }else {
                    mBuilder.setChannelId("id_channel");
                }
            }
            notificationManager.notify(m, mBuilder.build());

        }


    }


}



