package ru.vssemikoz.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class MainActivity extends AppCompatActivity {

    List<NewsItem> newsItems = new ArrayList<NewsItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        RecyclerView recyclerView =  findViewById(R.id.rv_newsfeed);
        NewsFeedAdapter adapter = new NewsFeedAdapter(newsItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initData(){
        newsItems.add(new NewsItem("Экс-глава управления ФСИН попытался покончить с собой в суде после приговора по делу о вымогательстве - Новая газета",
                "Экс-глава управления ФСИН Виктор Свиридов попытался застрелиться в Чертановском суде Москвы во время оглашения приговора по делу о вымогательстве (ч. 3 ст. 163 УК). Об этом сообщает РБК. \\r\\n\\r\\nСвиридов до приговора был под подпиской о невыезде. После слов судьи…"));
        newsItems.add(new NewsItem("Польша запросила у 'Газпрома' данные для дела против Nord Stream 2 - РИА НОВОСТИ",
                "Польский комитет конкуренции и защиты потребителей (UOKiK) запросил информацию у \\\"Газпрома\\\" для проведения расследования в отношении проекта газопровода... РИА Новости, 12.02.2020"));
        newsItems.add(new NewsItem("Рассекречена дешевая версия AirPods - Lenta.ru", "Apple планирует выпустить дешевую версию AirPods Pro. Об этом говорится в отчете знакомого с цепочкой поставок источника. В сообщении говорится, что компания намерена увеличить количество поставок девайсов iPad, Apple Watch, iMac и AirPods Pro Lite, чтобы удо…"));
        newsItems.add(new NewsItem("Microsoft выпустила эмулятор Windows 10X. Опробовать новую ОС могут все желающие - 3DNews", "Microsoft выпустила эмулятор операционной системы Windows 10X."));
    }
}
