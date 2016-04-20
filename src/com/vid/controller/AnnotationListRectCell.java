package com.vid.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.vid.commons.ConfirmBox;
import com.vid.comp.Jcomp.AbstractComp;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class AnnotationListRectCell<T> extends ListCell<T> {

	HBox hbox;
	Pane pane = new Pane();
	ImageView imageView;
	Label textLabel;
	private ObservableList<AbstractComp> listItems;
	private Map<Integer, AbstractComp> compList;

	int index;

	ListView<AbstractComp> listView;

	public AnnotationListRectCell(ListView<AbstractComp> listView, ObservableList<AbstractComp> listItems,
			Map<Integer, AbstractComp> compList) {
		super();
		this.listView = listView;
		this.compList = compList;
		this.listItems = listItems;
	}

	@Override
	public void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item instanceof AbstractComp) {
			AbstractComp comp = (AbstractComp) item;
			hbox = new HBox();
			HBox.setHgrow(pane, Priority.ALWAYS);
			hbox.setMaxHeight(15);
			hbox.setSpacing(5);
			imageView = new ImageView();
			imageView.setImage(comp.getGraphic());
			imageView.setFitWidth(15);
			imageView.setFitHeight(15);
			ImageView btView = new ImageView(comp.getDeleteGraphic());
			btView.setFitWidth(15);
			btView.setFitHeight(15);

			btView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					boolean display = ConfirmBox.display("", "Are u sure:");
					if (display) {
						int selectedIndex = listView.getSelectionModel().getSelectedIndex();
						listItems.remove(selectedIndex);
						compList.remove(selectedIndex);
						listView.refresh();
						listView.setItems(null);
						listView.setItems(listItems);
						//System.out.println(compList.size());
					}
				}
			});

			int newTime = comp.getStartTime();
			int endTime = comp.getEndTime();
			String s1 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(newTime),
					TimeUnit.MILLISECONDS.toMinutes(newTime)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(newTime)),
					TimeUnit.MILLISECONDS.toSeconds(newTime)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(newTime)));
			String s2 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(endTime),
					TimeUnit.MILLISECONDS.toMinutes(endTime)
							- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(endTime)),
					TimeUnit.MILLISECONDS.toSeconds(endTime)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(endTime)));
			textLabel = new Label(comp.getId() + " -- " + s1 + " -- " + s2);
			hbox.getChildren().addAll(btView, imageView, textLabel);
			if (comp.getGraphic() != null) {
				setGraphic(hbox);
			}

		} else {
			setText(item == null ? "null" : item.toString());
			setGraphic(null);
		}
	}

}
