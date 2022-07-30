package cn.qgstudio.view;

import cn.qgstudio.util.Md5Utils;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton loginButton;

    public LoginDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

//        buttonCancel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });

        // 单击十字时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
             }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
////                    loginActionPerformed();
//                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
//                    noSuchAlgorithmException.printStackTrace();
//                }
//            }
//        });
    }

//    private void loginActionPerformed() throws NoSuchAlgorithmException {
//        User user = getConditionUserFromLogOnFrm();
//
//        String jsonString = JSONObject.toJSONString(user);
//    }
//
//    private User getConditionUserFromLogOnFrm() throws NoSuchAlgorithmException {
//        String userName = this.usernameField.getText();
//        String password = new String(this.passwordField1.getPassword());
//
//        password = Md5Utils.getMD5(password);
//
//        return new User(userName,password);
//    }

    private void onOK() {
        // 在此处添加代码
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }

    public void  init() {
        LoginDialog dialog = new LoginDialog();
        dialog.setSize(500,400);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
