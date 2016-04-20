package com.myapplication.siva.sqlexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView idView,buckysText;
    EditText productBox;

    String TAG = "com.myapplication.siva.sqlexample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idView = (TextView) findViewById(R.id.buckysText2);
        productBox = (EditText) findViewById(R.id.buckysInput);
        buckysText = (TextView) findViewById(R.id.buckysText);

    }

    public void newProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);


        Product product =
                new Product(productBox.getText().toString());

        dbHandler.addProduct(product);
        productBox.setText("");
        buckysText.setText("Record Added");
    }


    public void removeProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result) {
            buckysText.setText("Record Deleted");
            productBox.setText("");

        } else
            buckysText.setText("No Match Found");
    }

    public void lookupProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Log.i(TAG,"Inside");

        // Product product =
        //       dbHandler.findProduct(productBox.getText().toString());
        String product = dbHandler.findProduct();
        Log.i(TAG,"Inside");
        if (product != null) {
            idView.setText(product);
        }
    }

    /*public void lookupProduct(){
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);
        String dbString = dbHandler.findProduct();
        idView.setText(dbString);
        productBox.setText("");
        quantityBox.setText("");
    }*/
}
