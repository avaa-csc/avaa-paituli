/**
 * 
 */
package fi.csc.avaa.paituli;

import java.util.Locale;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.csc.avaa.tools.vaadin.language.Translator;

/**
 * @author jmlehtin
 *
 */
public class PaituliApiUI extends UI {

	private static final long serialVersionUID = 1L;
	private Locale currentLocale;
	private Translator translator;

	@WebServlet(asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = PaituliApiUI.class)
	public static class Servlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;}
	 
	@Override
	protected void init(VaadinRequest request) {
		this.currentLocale = request.getLocale();
		this.translator = new Translator(this.currentLocale);
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponent(new ApiTable("Data available via API", this.translator));
		setContent(mainLayout);
	}
	
	@Override
	protected void refresh(VaadinRequest request) {
		init(request);
	}

}
