/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.view.schedule;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.animedetour.android.R;
import com.animedetour.sched.api.model.Panel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Panel tile view
 *
 * This is a view for the small card / list format of a panel.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class PanelView extends RelativeLayout
{
    /**
     * The title of the panel, displayed at the top of the card
     */
    private TextView title;

    /**
     * The description caption on the card
     */
    private TextView description;

    /**
     * The time format to use for the panel start and end time
     */
    final private static DateTimeFormatter timeFormat = DateTimeFormat.forPattern("hh:mma");

    public PanelView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.PanelView,
            0,
            0
        );

        String name = attributes.getString(R.styleable.PanelView_name);
        String description = attributes.getString(R.styleable.PanelView_description);

        LayoutInflater.from(context).inflate(R.layout.view_panel, this);
        this.title = (TextView) this.findViewById(R.id.view_panel_name);
        this.description = (TextView) this.findViewById(R.id.view_panel_description);

        this.title.setText(name);
        this.description.setText(description);
    }

    public PanelView(Context context)
    {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_panel, this);
        this.title = (TextView) this.findViewById(R.id.view_panel_name);
        this.description = (TextView) this.findViewById(R.id.view_panel_description);
    }

    /**
     * @param title The title of the panel, displayed at the top of the card
     */
    public void setTitle(String title)
    {
        this.title.setText(title);
    }

    /**
     * @param description The description caption on the card
     */
    public void setDescription(String description)
    {
        this.description.setText(description);
    }

    /**
     * Bind a panel object to display in the view
     *
     * @param panel The panel to sync data from
     */
    public void bind(Panel panel)
    {
        String timeRange = this.getTimeRangeString(panel.getStartDateTime(), panel.getEndDateTime());
        String inPreposition = this.getContext().getString(R.string.in_preposition);
        String venue = panel.getVenue();
        String fullDescription = timeRange + " " + inPreposition + " " + venue;

        this.setDescription(fullDescription);
        this.setTitle(panel.getName());
    }

    /**
     * @param start The start time of the panel
     * @param end The end time of the panel
     * @return A formated timespan of the start and end time of the panel, e.g. "2:00PM - 6:00PM"
     */
    protected String getTimeRangeString(DateTime start, DateTime end)
    {
        return this.timeFormat.print(start) + " - " + this.timeFormat.print(end);
    }
}