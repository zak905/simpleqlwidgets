package com.simpleql.client.table;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

public class MyButton extends Composite{

    String name;
    public MyButton(String name) {
        this.name =name;

        Button b = new Button(name);
        b.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                myonclick();

            }
        });
        initWidget(b);
        declareMethod(this);
    }

    public native void declareMethod(MyButton myWidget) /*-{
            $wnd.myWidget = myWidget;

    }-*/;

    public native void myonclick() /*-{
        $wnd.myWidget.@com.simpleql.client.table.MyButton::doSomething()();
    }-*/;

    public void doSomething() {
        System.out.println("test");
    }
}
