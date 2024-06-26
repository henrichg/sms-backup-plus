package com.zegoggles.smssync.activity.fragments;

import android.os.Bundle;
import android.os.Handler;

import androidx.preference.PreferenceFragmentCompat;

import com.zegoggles.smssync.App;
import com.zegoggles.smssync.R;
import com.zegoggles.smssync.activity.events.AutoBackupSettingsChangedEvent;
import com.zegoggles.smssync.preferences.Preferences;

public abstract class SMSBackupPreferenceFragment extends PreferenceFragmentCompat {
    protected Preferences preferences;
    private Handler handler;

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        preferences = new Preferences(getContext(), getPreferenceManager().getSharedPreferences());
        handler = new Handler();
    }

    void addPreferenceListener(String... prefKeys) {
        addPreferenceListener(new AutoBackupSettingsChangedEvent(), prefKeys);
    }

    void addPreferenceListener(final Object event, String... prefKeys) {
        for (String prefKey : prefKeys) {
            //noinspection DataFlowIssue
            findPreference(prefKey).setOnPreferenceChangeListener(
                    (preference, newValue) -> {
                        handler.post(() -> App.post(event));
                        return true;
                    });
        }
    }
}
