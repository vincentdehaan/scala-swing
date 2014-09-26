package scala.swing.examples.tutorials.components

import scala.swing._

/*
 * Tutorial: How to Use Password Fields
 * http://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html
 * 
 * Source code reference:
 * http://docs.oracle.com/javase/tutorial/uiswing/examples/components/PasswordDemoProject/src/components/PasswordDemo.java
 *
 * PasswordDemo.scala requires no other files.
 */
class PasswordDemo(val controllingFrame: Frame) extends FlowPanel {
  private val OK = "ok"
  private val Help = "help"
  val passwordField = new PasswordField(10)
  val label = new Label("Enter the password: ")
  label.peer.setLabelFor(passwordField.peer)

  val buttonPane = createButtonPanel()
  val textPane = new FlowPanel()
  textPane.contents += label
  textPane.contents += passwordField
  //
  contents += textPane
  contents += buttonPane

  def createButtonPanel(): GridPanel = {
    val p = new GridPanel(0, 1)
    val okButton = new Button(Action("OK") {
      val input: Array[Char] = passwordField.password
      if (PasswordDemo.isPasswordCorrect(input)) {
        Dialog.showMessage(p,
          "Success! You typed the right password.",
          "Passord Success", Dialog.Message.Info, Swing.EmptyIcon)
      } else {
        Dialog.showMessage(p,
          "Invalid password. Try again.",
          "Error Message",
          Dialog.Message.Error, Swing.EmptyIcon)
      }
      //Zero out the possible password, for security.
      for (i <- 0 until input.length) {
        input(i) = '0'
      }
      passwordField.selectAll()
      passwordField.requestFocusInWindow()
    })
    val helpButton = new Button(Action("Help") {
      Dialog.showMessage(p,
          "You can get the password by searching this example's\n"
              + "source code for the string \"correctPassword\".\n"
              + "Or look at the section How to Use Password Fields in\n"
              + "the components section of The Java Tutorial.",
          "Passord Help", Dialog.Message.Info, Swing.EmptyIcon)
    })
    p.contents += okButton
    p.contents += helpButton
    listenTo(okButton)
    listenTo(helpButton)
    p
  }
}

object PasswordDemo extends SimpleSwingApplication {
  /**
   * Checks the passed-in array against the correct password.
   * After this method returns, you should invoke eraseArray
   * on the passed-in array.
   */
  def isPasswordCorrect(input: Array[Char]): Boolean = {
    val correctPassword = Array[Char]('b', 'u', 'g', 'a', 'b', 'o', 'o')

    val isCorrect = if (input.length != correctPassword.length) {
      false;
    } else {
      var same = true
      for (i <- 0 until input.length) {
        same &= input(i) == correctPassword(i)
      }
      same
    }

    //Zero out the password.
    // Arrays.fill(correctPassword,'0');
    for (i <- 0 until correctPassword.length)
      correctPassword.update(i, '0')
    isCorrect;
  }
  def top = new MainFrame() {
    title = "PasswordDemo"
    //Create and set up the content pane.
    contents = new PasswordDemo(this)
  }
}