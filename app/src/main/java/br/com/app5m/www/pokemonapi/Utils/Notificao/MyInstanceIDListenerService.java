package br.com.app5m.www.pokemonapi.Utils.Notificao;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;


public class MyInstanceIDListenerService extends InstanceIDListenerService  {

    @Override
    public void onTokenRefresh() {

        Intent it = new Intent(this, RegistrationIntentService.class);
        startService(it);
    }
}
