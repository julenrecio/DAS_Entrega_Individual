package com.example.entregaindividual;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import java.util.Locale;

public class Preferencias {

    private static Preferencias misPreferencias;
    private final SharedPreferences preferencias;
    private static final String KEY_SESSION = "session";
    private static final String KEY_LANGUAGE = "language";

    // Patron Singleton
    public static Preferencias getPreferencias(@Nullable Context context) {
        if (misPreferencias == null) {
            misPreferencias = new Preferencias(context);
        }
        return misPreferencias;
    }

    private Preferencias(Context context) {
        preferencias = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // Guardar el id del usuario
    public void setSessionId(String sessionId) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(KEY_SESSION, sessionId);
        editor.apply();
    }

    // Obtener el id del usuario
    public String getSessionId() {
        return preferencias.getString(KEY_SESSION, "");
    }

    // Guardar el idioma escogido por el usuario
    public void setLanguage(String language) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(KEY_LANGUAGE, language);
        editor.apply();
    }

    // Obtener el idioma
    private String getLanguage() {
        return preferencias.getString(KEY_LANGUAGE, "");
    }

    // Establecer el idioma de la aplicación
    public void applyLanguage(Context context) {
        String language = getLanguage();
        if (!language.isEmpty()) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);

            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }
    }
}