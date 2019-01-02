package br.com.app5m.www.pokemonapi.Utils.Notificao;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import br.com.sulpolo.sulpolo.Util.CadastroUtil;
import br.com.sulpolo.sulpolo.Webservice.Model.CadastroModel;


public class RegistrationIntentService extends IntentService {
    public static final String LOG = "GcmDiabao";

    CadastroUtil cadastroUtil;
    CadastroModel cadastroModel;


    public RegistrationIntentService() {
        super(LOG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        cadastroUtil = new CadastroUtil(getApplicationContext());
        cadastroModel = new CadastroModel(getApplicationContext(), null);


        synchronized (LOG) {
            InstanceID instanceID = InstanceID.getInstance(this);
            try {

                final String registration_id = instanceID.getToken("471165877401", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                cadastroModel.gcm_enviar(registration_id);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
