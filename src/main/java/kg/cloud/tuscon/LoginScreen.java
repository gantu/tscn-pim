package kg.cloud.tuscon;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class LoginScreen extends VerticalLayout {
	
	/**
	 * 
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyVaadinApplication app;
	
	public LoginScreen(MyVaadinApplication app){
		this.app=app;
		
		app.getMainWindow().setCaption("Login");
		setSizeFull();
		final HorizontalLayout languageBar=new HorizontalLayout();
		languageBar.setHeight("50px");
		addComponent(languageBar);
		setComponentAlignment(languageBar, Alignment.TOP_RIGHT);
		
				
		
		Panel loginPanel=new Panel("Login");
		loginPanel.setWidth("400px");
		
		LoginForm loginForm=new LoginForm();
		loginForm.setPasswordCaption("Password");
		loginForm.setUsernameCaption("Username");
		loginForm.setLoginButtonCaption("Login");

		loginForm.setHeight("120px");
		loginForm.addListener(new MyLoginListener(this.app, loginForm));
		loginPanel.addComponent(loginForm);

		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

		HorizontalLayout footer = new HorizontalLayout();
		footer.setHeight("50px");
		addComponent(footer);
		
		

		
	}
	
	private static class MyLoginListener implements LoginForm.LoginListener{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private MyVaadinApplication app;
		private LoginForm loginForm;		
		
		public MyLoginListener(MyVaadinApplication app,LoginForm loginForm){
			this.app=app;
			this.loginForm=loginForm;			
		}
		
		public void onLogin(LoginEvent event){
			String username=event.getLoginParameter("username");
			String password=event.getLoginParameter("password");
			try
			{
				MyVaadinApplication.getInstance().login(username, password);
                                
				// Switch to the protected view
				app.getMainWindow().setContent(new AuthenticatedScreen(app));
			}
			catch (UnknownAccountException uae)
			{
				this.loginForm.getWindow().showNotification("Invalid User", Notification.TYPE_ERROR_MESSAGE);
			}
			catch (IncorrectCredentialsException ice)
			{
				this.loginForm.getWindow().showNotification("Invalid User", Notification.TYPE_ERROR_MESSAGE);
			}
			catch (LockedAccountException lae)
			{
				this.loginForm.getWindow().showNotification("Invalid User", Notification.TYPE_ERROR_MESSAGE);
			}
			catch (ExcessiveAttemptsException eae)
			{
				this.loginForm.getWindow().showNotification("Invalid User", Notification.TYPE_ERROR_MESSAGE);
			}
			catch (AuthenticationException ae)
			{
				this.loginForm.getWindow().showNotification("Invalid User", Notification.TYPE_ERROR_MESSAGE);
			}
			catch (Exception ex)
			{
				this.loginForm.getWindow().showNotification("Exception " + ex.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			}
		}
		
	}


}
