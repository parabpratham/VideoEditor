package com.vid.commons;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class VideoSlider extends JSlider {

	private static final long serialVersionUID = -1957712352112131445L;

	private Timer timer;
	private boolean syncTimeline = false;
	private DirectMediaPlayerComponent mediaPlayerComponent;

	public VideoSlider() {

	}

	public VideoSlider(DirectMediaPlayerComponent mediaPlayerComponent) {

		super(0, 10000, 0);
		this.mediaPlayerComponent = mediaPlayerComponent;

		setMajorTickSpacing(10);
		setMajorTickSpacing(5);
		setPaintTicks(true);

		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!syncTimeline) // only if user moves the slider by hand
				{
					if (getValueIsAdjusting()) // and the slider is
												// fixed
					{
						// recalc to 0.x percent value
						mediaPlayerComponent.getMediaPlayer().setPosition((float) getValue() / 100.0f);
					}
				}
			}
		});

	}

	public void updateTime() {
		if (mediaPlayerComponent.getMediaPlayer().isPlaying()) {
			long millis = mediaPlayerComponent.getMediaPlayer().getTime();
			String s = String.format("%02d:%02d:%02d", // dont know why normal
														// Java date utils
														// doesn't format the
														// time right
					TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
			// setTitle(ms.format(new Time(sec)));
			syncTimeline = true;
			setValue(Math.round(mediaPlayerComponent.getMediaPlayer().getPosition() * 100));
			syncTimeline = false;
		}
	}

}
