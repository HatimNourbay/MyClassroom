package com.example.hatim.myclassroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getResults(getConnection(),"dc=isep.fr","(uid=hnourbay)");
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

    public static List<SearchResultEntry> getResults(LDAPConnection connection, String baseDN, String filter) throws LDAPSearchException {
        SearchResult searchResult;

        if (connection.isConnected()) {
            searchResult = connection.search(baseDN, SearchScope.ONE, filter);

            return searchResult.getSearchEntries();

        }

        return null;
    }

    public static LDAPConnection getConnection() throws LDAPException {
        // host, port, username and password
        return new LDAPConnection("ldap.isep.fr", 389, "uid=hnourbay, ou=People, dc=isep.fr", "my_password");
    }
}