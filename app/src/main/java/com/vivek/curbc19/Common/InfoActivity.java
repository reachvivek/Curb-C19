package com.vivek.curbc19.Common;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.vivek.curbc19.HelperClasses.InfoAdapter.InfoAdapter;
import com.vivek.curbc19.HelperClasses.InfoAdapter.InfoItem;
import com.vivek.curbc19.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

public class InfoActivity extends AppCompatActivity {

    private InfoAdapter infoAdapter;
    private LinearLayout layoutInfoIndicators;
    private MaterialButton btn_toggle_action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        layoutInfoIndicators = findViewById(R.id.layoutInfoIndicators);
        btn_toggle_action = findViewById(R.id.btn_toggle_action);

        setupInfoItems();

        ViewPager2 infoViewPager = findViewById(R.id.infoViewPager);
        infoViewPager.setAdapter(infoAdapter);

        setupInfoIndicators();
        setCurrentInfoIndicator(0);

        infoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentInfoIndicator(position);
            }
        });

        btn_toggle_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoViewPager.getCurrentItem() +1 < infoAdapter.getItemCount()) {
                    infoViewPager.setCurrentItem(infoViewPager.getCurrentItem()+1);
                }
                else {
                    finish();
                }
            }
        });
    }

    private void setupInfoItems() {
        List<InfoItem> infoItems = new ArrayList<>();

        InfoItem itemFirst = new InfoItem();
        itemFirst.setTitle("All You Need To Know About COVID-19");
        itemFirst.setDescription("Coronavirus Disease 2019 also known as COVID-19 is a respiratory illness that can spread from person to person.\n\nThe virus that causes COVID-19 is a novel coronavirus that was first identified during an investigation into an outbreak in Wuhan, China.");
        itemFirst.setImage(R.drawable.info1);

        InfoItem itemSecond = new InfoItem();
        itemSecond.setTitle("Symptoms");
        itemSecond.setDescription("Patients with COVID-19 have had mild to severe respiratory illness with symptoms of\n\n" +
                " •\tFever\n\n" +
                " •\tCough\n\n" +
                " •\tShortness of Breath");
        itemSecond.setImage(R.drawable.info2);

        InfoItem itemThird = new InfoItem();
        itemThird.setTitle("Complications of COVID 19");
        itemThird.setDescription("Some patients have pneumonia in both lungs, multi-Organ failure and in some cases Death.");
        itemThird.setImage(R.drawable.info3);

        InfoItem itemFourth = new InfoItem();
        itemFourth.setTitle("Precautionary Measures");
        itemFourth.setDescription("People can help protect themselves from Respiratory illness with everyday preventive actions. \n\n" +
                "•\tAvoid close contact with people who are sick.\n\n" +
                "•\tAvoid touching your eyes, nose, and mouth with unwashed hands.\n\n" +
                "•\tWash your hands often with soap and water for at least 20 seconds.\n\n" +
                "•\tUse an alcohol-based hand sanitizer that contains at least 60% alcohol if soap and water are not available.");
        itemFourth.setImage(R.drawable.info4);

        InfoItem itemFifth = new InfoItem();
        itemFifth.setTitle("Things to do if you are Sick\n");
        itemFifth.setDescription("•\tStay home when you are sick.\n\n" +
                "•\tCover your cough or sneeze with a tissue, then throw the tissue in the trash.\n\n" +
                "•\tClean and disinfect frequently touched objects and surfaces.");
        itemFifth.setImage(R.drawable.info5);

        InfoItem itemSixth = new InfoItem();
        itemSixth.setTitle("Vaccination");
        itemSixth.setDescription("There is currently no vaccine to protect against COVID-19. The best way to prevent infection is to take everyday preventive actions, like avoiding close contact with people who are sick and washing your hands often.");
        itemSixth.setImage(R.drawable.info6);

        InfoItem itemSeventh = new InfoItem();
        itemSeventh.setTitle("Treatment");
        itemSeventh.setDescription("There is no specific antiviral treatment for COVID-19. People with COVID-19 can seek medical care to help relieve symptoms.");
        itemSeventh.setImage(R.drawable.info7);

        InfoItem itemEight = new InfoItem();
        itemEight.setTitle("Myths-Busters");
        itemEight.setDescription("1. Cold Weather and Snow CANNOT kill the Corona Virus.\n\n" +
                "2. The Coronavirus CAN be transmitted in areas with HOT and HUMID climates.\n\n" +
                "3. There is NO evidence that companion animals/pets such as dogs or cats can transmit the Coronavirus.\n\n" +
                "4. Taking a hot bath DOES NOT prevent the Coronavirus.\n\n" +
                "5. Hand dryers are NOT effective in killing the Coronavirus.\n\n" +
                "6. Thermal scanners CAN detect if people have a Fever but CANNOT detect whether or not someone has the Coronavirus.\n\n" +
                "7. Spraying Alcohol or Chlorine all over your body WILL NOT kill viruses that have already entered your body.\n\n" +
                "8. Antibiotics DO NOT work against viruses, antibiotics only work against Bacteria.\n\n" +
                "9. To date, there is NO specific medicine recommended to prevent or treat the Coronavirus.");
        itemEight.setImage(R.drawable.info8);

        infoItems.add(itemFirst);
        infoItems.add(itemSecond);
        infoItems.add(itemThird);
        infoItems.add(itemFourth);
        infoItems.add(itemFifth);
        infoItems.add(itemSixth);
        infoItems.add(itemSeventh);
        infoItems.add(itemEight);

        infoAdapter = new InfoAdapter(infoItems);

    }

    private void setupInfoIndicators() {
        ImageView[] indicators = new ImageView[infoAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0; i<indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.info_indicator_active
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutInfoIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentInfoIndicator(int index) {
        int childCount = layoutInfoIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView)layoutInfoIndicators.getChildAt(i);
            if (i==index)
            {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.info_indicator_active)
                );
            }
            else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.info_indicator_inactive)
                );
            }
        }
        if (index == infoAdapter.getItemCount()-1) {
            btn_toggle_action.setText("GOTCHA!");
        }
        else {
            btn_toggle_action.setText("NEXT...");
        }
    }

}
