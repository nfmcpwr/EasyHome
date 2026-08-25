package net.nfmcpwr.EasyHome;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ColorPicker extends ConstraintLayout
{
    private boolean isExpanded;
    private byte r;
    private byte g;
    private byte b;
    private String colorString;
    private TextView titleText;
    private TextView previewText;
    private ImageButton expandButton;
    private ConstraintLayout barExpand;
    private SeekBar bar_r;
    private SeekBar bar_g;
    private SeekBar bar_b;
    
    public ColorPicker(Context context)
    {
        this(context, null);
    }
    
    public ColorPicker(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public ColorPicker(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }
    
    private void init(Context context, AttributeSet attrs, int defStyle)
    {
        this.isExpanded = false;
        this.r = 0x00;
        this.g = 0x00;
        this.b = 0x00;
        this.colorString = "#000000";
        
        LayoutInflater.from(context).inflate(R.layout.color_picker, this);
        
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorPicker, defStyle, 0);
        
        this.titleText = findViewById(R.id.cp_title);
        if (ta.getString(R.styleable.ColorPicker_title) != null)
        {
            this.titleText.setText(ta.getString(R.styleable.ColorPicker_title));
        }
        
        this.previewText = findViewById(R.id.cp_preview);
        this.previewText.setText("#000000");
        
        this.expandButton = findViewById(R.id.cp_expandButton);
        this.barExpand = findViewById(R.id.cp_barExpand);
        this.expandButton.setOnClickListener(view ->
        {
            if (isExpanded)
            {
                isExpanded = false;
                expandButton.setImageResource(R.drawable.expand_down_24px);
                
                barExpand.setVisibility(View.GONE);
            }
            else
            {
                isExpanded = true;
                expandButton.setImageResource(R.drawable.expand_up_24px);
                
                barExpand.setVisibility(View.VISIBLE);
            }
        });
        
        this.bar_r = findViewById(R.id.cp_r);
        this.bar_r.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bu)
            {
                r = (byte)i;
                colorString = "#" + String.format("%02x%02x%02x", r, g, b).toUpperCase();
                previewText.setText(colorString);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            
            }
        });
        
        this.bar_g = findViewById(R.id.cp_g);
        this.bar_g.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bu)
            {
                g = (byte)i;
                colorString = "#" + String.format("%02x%02x%02x", r, g, b).toUpperCase();
                previewText.setText(colorString);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            
            }
        });
        
        this.bar_b = findViewById(R.id.cp_b);
        this.bar_b.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bu)
            {
                b = (byte)i;
                colorString = "#" + String.format("%02x%02x%02x", r, g, b).toUpperCase();
                previewText.setText(colorString);
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            
            }
        });
        
        setColor("#000000");
        ta.recycle();
    }
    
    public byte getR()
    {
        return this.r;
    }
    
    public byte getG()
    {
        return this.g;
    }
    
    public byte getB()
    {
        return this.b;
    }
    
    public void setColor(byte r, byte g, byte b)
    {
        setColor(Color.valueOf(Color.argb(0xFF, r, g, b)));
    }
    
    public void setColor(Color color)
    {
        this.bar_r.setProgress((byte)color.red());
        this.bar_g.setProgress((byte)color.green());
        this.bar_b.setProgress((byte)color.blue());
    }
    
    public void setColor(String colorString)
    {
        setColor(Color.valueOf(Color.parseColor(colorString)));
    }
    
    public void setTitle(String title)
    {
        this.titleText.setText(title);
    }
}
