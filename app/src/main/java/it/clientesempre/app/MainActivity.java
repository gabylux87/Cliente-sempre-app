package it.clientesempre.app;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {

    LinearLayout root, list;
    TextView total;
    final ArrayList<String> clients = new ArrayList<>();

    int dp(float v) {
        return (int) (v * getResources().getDisplayMetrics().density + 0.5f);
    }

    TextView text(String s, int size, boolean bold) {
        TextView v = new TextView(this);
        v.setText(s);
        v.setTextSize(size);
        v.setTextColor(Color.rgb(25, 40, 36));
        v.setPadding(0, dp(6), 0, dp(6));

        if (bold) {
            v.setTypeface(null, Typeface.BOLD);
        }

        return v;
    }

    Button button(String s) {
        Button b = new Button(this);
        b.setText(s);
        b.setAllCaps(false);
        return b;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        build();
    }

    void build() {

        ScrollView scroll = new ScrollView(this);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(25), dp(20), dp(30));
        root.setBackgroundColor(Color.rgb(244, 247, 246));

        scroll.addView(root);
        setContentView(scroll);

        TextView logo = text("CLIENTE SEMPRE", 28, true);
        logo.setTextColor(Color.rgb(20, 61, 53));
        root.addView(logo);

        root.addView(text(
                "Il cliente giusto, al momento giusto.",
                15,
                false
        ));

        Space space = new Space(this);
        root.addView(
                space,
                new LinearLayout.LayoutParams(1, dp(18))
        );

        total = text("0 clienti attivi", 18, true);
        root.addView(total);

        root.addView(text(
                "Recupera clienti • Pianifica richiami • Anticipa i picchi",
                14,
                false
        ));

        Button add = button("+ Aggiungi cliente");
        root.addView(add);

        add.setOnClickListener(v -> addClient());

        root.addView(text("PROSSIMI RICHIAMI", 17, true));

        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        root.addView(list);

        root.addView(text("DEMAND SHAPING", 17, true));

        root.addView(text(
                "Distribuisci il lavoro prima dei periodi di punta con prenotazioni anticipate.",
                14,
                false
        ));

        Button campaign = button("Crea campagna anticipata");
        root.addView(campaign);

        campaign.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Modulo campagne pronto per il prossimo aggiornamento",
                        Toast.LENGTH_SHORT
                ).show()
        );

        render();
    }

    void addClient() {

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(20), 0, dp(20), 0);

        EditText name = new EditText(this);
        name.setHint("Nome cliente");
        box.addView(name);

        EditText service = new EditText(this);
        service.setHint("Servizio (es. tagliando)");
        box.addView(service);

        new AlertDialog.Builder(this)
                .setTitle("Nuovo cliente")
                .setView(box)
                .setNegativeButton("Annulla", null)
                .setPositiveButton("Salva", (dialog, which) -> {

                    String n = name.getText().toString().trim();
                    String s = service.getText().toString().trim();

                    if (!n.isEmpty()) {

                        if (s.isEmpty()) {
                            s = "Servizio";
                        }

                        clients.add(n + "|" + s);
                        render();
                    }
                })
                .show();
    }

    void render() {

        list.removeAllViews();

        total.setText(clients.size() + " clienti attivi");

        if (clients.isEmpty()) {

            list.addView(text(
                    "Nessun cliente ancora. Aggiungi il primo cliente per provare l'app.",
                    14,
                    false
            ));

            return;
        }

        for (String client : clients) {

            String[] data = client.split("\\|");

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(
                    dp(14),
                    dp(10),
                    dp(14),
                    dp(10)
            );

            card.setBackgroundColor(Color.WHITE);

            card.addView(text(data[0], 17, true));
            card.addView(text(data[1], 14, false));

            Button reminder = button("Prepara richiamo");

            reminder.setOnClickListener(v ->
                    Toast.makeText(
                            this,
                            "Richiamo pronto per " + data[0],
                            Toast.LENGTH_SHORT
                    ).show()
            );

            card.addView(reminder);
            list.addView(card);

            Space gap = new Space(this);

            list.addView(
                    gap,
                    new LinearLayout.LayoutParams(1, dp(10))
            );
        }
    }
}
