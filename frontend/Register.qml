import QtQuick 2.0
import QtQuick.Controls 2.15
import QtQuick.Layouts 1.0

Item {
    ColumnLayout {
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
        anchors.margins: 10
        spacing: 3

        TextField {
            id: userName
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            Layout.fillWidth: true
            placeholderText: "Username"
        }

        TextField {
            id: regPassword
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            placeholderText: "Password1"
            echoMode: TextInput.PasswordEchoOnEdit
        }

        TextField {
            id: userEmail
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            Layout.fillWidth: true
            placeholderText: "example@net.com"
        }

        TextField {
            id: userTelephone
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            Layout.fillWidth: true
            placeholderText: "+36201234567 or 06201234567"
        }
        Popup {
            id: popup
            height: 300
            width: 300
            anchors.centerIn: Overlay.overlay
            padding: 10

                contentItem: Text {
                    text: "LOGGED IN"
                }
       }

        Button {
            id: proccessButton
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 15
            text: "REGISTRATION"
            enabled: userName.text.length > 0 && regPassword.text.length > 0
            onClicked: function() {
                backend.registration(userName.text,regPassword.text,userEmail.text,userTelephone.text);
                popup.open();
            }
        }
    }
}
