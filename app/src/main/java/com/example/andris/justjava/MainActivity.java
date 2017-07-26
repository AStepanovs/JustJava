package com.example.andris.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.andris.justjava.R.id.chocolate;
import static com.example.andris.justjava.R.id.whippedCream;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCB = (CheckBox) findViewById(whippedCream);
        boolean hasWhippedCream = whippedCreamCB.isChecked();
        Log.v("Main activity", "Has whipped cream: " + hasWhippedCream);
        CheckBox chocolateCB = (CheckBox) findViewById(chocolate);
        boolean hasChocolate = chocolateCB.isChecked();
        Log.v("Main activity", "Has chocolate: " + hasChocolate);
        TextView customerName = (TextView) findViewById(R.id.customerName);
        String name = customerName.getText().toString();
        int priceOfOrder = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(priceOfOrder,hasWhippedCream, hasChocolate, name);

        Intent sendOrder = new Intent(Intent.ACTION_SENDTO);
        sendOrder.setData(Uri.parse("mailto:"));
        sendOrder.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + name);
        sendOrder.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(sendOrder.resolveActivity(getPackageManager()) != null){
            startActivity(sendOrder);
        }

    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){
        String summary = getString(R.string.name) + ": " + name  + "\n" + getString(R.string.add_cream) + " " + hasWhippedCream + "\n" + getString(R.string.add_choco) + " " + hasChocolate+ "\n" + getString(R.string.quantity) + ": " + quantity + "\n" + getString(R.string.total) + price + "\n" + getString(R.string.thank_you);
        return summary;

    }


    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) {
            price = price + 1;
        }
        if (hasChocolate){
            price = price + 2;
        }
        int totalPrice = price * quantity;
        return totalPrice;

    }

    public void increment(View view) {
        quantity = quantity + 1;
        if(quantity>100){
            quantity = 100;
            Toast.makeText(this, getString(R.string.too_much), Toast.LENGTH_SHORT).show();
        }

        displayQuantity(quantity);

    }

    public void decrement(View view) {
        quantity = quantity - 1;
        if(quantity<1){
            quantity = 1;
            Toast.makeText(this, getString(R.string.too_few), Toast.LENGTH_SHORT).show();
        }

        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }
}