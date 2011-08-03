package com.hobby.entity.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import com.hobby.entity.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.search.client.Result;
import com.google.gwt.search.client.ResultClass;
import com.google.gwt.search.client.ResultSetSize;
import com.google.gwt.search.client.SearchControl;
import com.google.gwt.search.client.SearchControlOptions;
import com.google.gwt.search.client.SearchResultsHandler;
import com.google.gwt.search.client.WebResult;
import com.google.gwt.search.client.WebSearch;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class EntityMiner implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button submitButton = new Button("Submit");
		final Button refreshButton = new Button("Refresh");
		final CheckBox cornellCheck = new CheckBox("cornell");
		final CheckBox floridaCheck = new CheckBox("florida");
		final CheckBox indianaCheck = new CheckBox("indiana");
		final CheckBox wikiCheck = new CheckBox("wiki");
		final TextArea articleField = new TextArea();
		articleField.setCharacterWidth(80);
		articleField.setVisibleLines(30);
		articleField.setText("Cornell University " +
				"is an Ivy League university located " +
				"in Ithaca, New York. It is a private land-grant university which receives " +
				"funding from the State of New York for certain educational missions. Cornell " +
				"was founded in 1865 by Ezra Cornell and Andrew Dickson White as a co-educational," +
				" non-sectarian institution where admission was offered irrespective of religion or race." +
				"The university was inaugurated on October 7, 1868, and 412 men were enrolled the next" +
				" day.[12] Scientists Louis Agassiz and James Crafts were among the faculty members.[11]" +
				" Two years later, Cornell admitted its first women students, making it the first coeducational" +
				" school among what came to be known as the Ivy League.");
		final Label errorLabel = new Label();
		final Label charCount = new Label();

		// We can add style names to widgets
		submitButton.addStyleName("sendButton");
		refreshButton.addStyleName("refreshButton");
		cornellCheck.setText("VIVO Cornell");
		floridaCheck.setText("VIVO Florida");
		indianaCheck.setText("VIVO Indiana");
		wikiCheck.setText("I prefer Wiki links");
		

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("articleFieldContainer").add(articleField);
		RootPanel.get("submitButtonContainer").add(submitButton);
		RootPanel.get("refreshButtonContainer").add(refreshButton);
		RootPanel.get("cornellCheckBoxContainer").add(cornellCheck);
		RootPanel.get("floridaCheckBoxContainer").add(floridaCheck);
		RootPanel.get("indianaCheckBoxContainer").add(indianaCheck);
		RootPanel.get("wikiCheckBoxContainer").add(wikiCheck);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		RootPanel.get("charCountContainer").add(charCount);

		// Focus the cursor on the name field when the app loads
		refreshButton.setFocus(false);
		refreshButton.setEnabled(false);
		articleField.setFocus(true);
		articleField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Article with most probable links");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setSize("80", "30");
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		
	
		
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				refreshButton.setEnabled(true);
				refreshButton.setFocus(true);
				submitButton.setEnabled(false);
				submitButton.setFocus(false);
				cornellCheck.setFocus(false);
				cornellCheck.setFocus(false);
				floridaCheck.setFocus(false);
				floridaCheck.setFocus(false);
				indianaCheck.setFocus(false);
				indianaCheck.setFocus(false);
				wikiCheck.setFocus(false);
				wikiCheck.setFocus(false);
			}
		});
		
		//Add a handler to refresh the textarea
		refreshButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				articleField.setText("");
				submitButton.setEnabled(true);
				submitButton.setFocus(true);
				refreshButton.setEnabled(false);
				refreshButton.setFocus(false);
				cornellCheck.setFocus(true);
				cornellCheck.setEnabled(true);
				cornellCheck.setChecked(false);
				floridaCheck.setFocus(true);
				floridaCheck.setEnabled(true);
				floridaCheck.setChecked(false);
				indianaCheck.setFocus(true);
				indianaCheck.setEnabled(true);
				indianaCheck.setChecked(false);
				wikiCheck.setFocus(true);
				wikiCheck.setEnabled(true);
				wikiCheck.setChecked(false);
			}
			
		});
		
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler, ChangeHandler, KeyPressHandler {
			
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}
			
			@Override
			public void onChange(ChangeEvent event) {
				charCount.setText("");
				charCount.setText("Number of characters : " + articleField.getText().length());	
			}
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				charCount.setText("");
				charCount.setText("Number of characters : " + articleField.getText().length());
				
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = articleField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Text length should be between 400 - 1000 characters");
					return;
				}

				// Then, we send the input to the server.
				submitButton.setEnabled(false);
				serverResponseLabel.setText("");
				greetingService.getLinkedArticle(textToServer,
						new AsyncCallback<List<String>>() {
					 
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Text parse - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(List<String> result) {
								dialogBox.setText("Article with most probable links");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								getLinks(result);	
								dialogBox.center();
								closeButton.setFocus(true);
							}
							
							public void getLinks(List<String> terms){
								
								String term;
								String uri;
								ListIterator<String> itr = terms.listIterator();
								while(itr.hasNext()){
									term = itr.next();
									setArticle(term);	
								}
							}
							
							public void setArticle(final String term){
								SearchControlOptions option = new SearchControlOptions();
								WebSearch websearch = new WebSearch();
								websearch.setResultSetSize(ResultSetSize.SMALL);
								if(cornellCheck.isChecked())
										websearch.setSiteRestriction("http://vivo.cornell.edu/");
								else if(floridaCheck.isChecked())
									websearch.setSiteRestriction("http://vivo.ufl.edu/");
								else if(indianaCheck.isChecked())
									websearch.setSiteRestriction("http://vivo.iu.edu/");
								else if(wikiCheck.isChecked())
									websearch.setSiteRestriction("en.wikipedia.org/");
								
								option.add(websearch);
								SearchControl control = new SearchControl(option);
								control.addSearchResultsHandler(new SearchResultsHandler(){
									
									public void onSearchResults(SearchResultsEvent event) {
										// TODO Auto-generated method stub		
										int currentRow = 0;
										JsArray<? extends Result> results = event.getResults();
											if(results.get(0).getResultClass().equals(ResultClass.WEB_SEARCH_RESULT)){
												currentRow++;
												WebResult result = (WebResult) results.get(0);
												String text = articleField.getText();
												articleField.setText(articleField.getText().replaceFirst(term, "<a href='"+result.getUrl()+"' target='_blank'>"+term+"</a>"));
												serverResponseLabel.setHTML(text.replaceFirst(term, "<a href='"+result.getUrl()+"' target='_blank'>"+term+"</a>"));
											}
									}
								});
	                               control.execute(term);  
							}
						});
			}


			
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		submitButton.addClickHandler(handler);
		articleField.addKeyUpHandler(handler);
		articleField.addChangeHandler(handler);
		articleField.addKeyPressHandler(handler);
	}
}
