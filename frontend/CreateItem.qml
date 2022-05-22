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
            id: itemName
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            placeholderText: "Itemname"
        }

        TextArea {
            id: description
            Layout.preferredWidth: 200
            Layout.preferredHeight: 200
            placeholderText: "This produsct is..."
        }


        TextField {
            id: price
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            placeholderText: "2000"
        }

        /*DropArea {
                id: dropArea
                Layout.preferredWidth: 200
                Layout.preferredHeight: 40
                onEntered: {
                    drag.accept (Qt.LinkAction);
                }
                onDropped: {
                }
                onExited: {
                }
            }*/


        ComboBox {
          id: colorCombo
          Layout.preferredWidth: 200
          Layout.preferredHeight: 40
          font.pointSize: 10
          editable: false
          model: ListModel {
           id: model2
           ListElement { text: "BLACK";  }
           ListElement { text: "WHITE";  }
           ListElement { text: "GREY";   }
           ListElement { text: "RED";    }
           ListElement { text: "GREEN";  }
           ListElement { text: "BLUE";   }
           ListElement { text: "PURPLE"; }
           ListElement { text: "YELLOW"; }
           ListElement { text: "PINK";   }
         }
         onAccepted: {
          if (colorCombo.find(currentText) === -1) {
             model2.append({text: editText})
             currentIndex =colorCombo.find(editText)
           }
         }
        }

        ComboBox {
          id: gender
          Layout.preferredWidth: 200
          Layout.preferredHeight: 40
          font.pointSize: 10
          editable: false
          model: ListModel {
           id: model
           ListElement { text: "MAN";    }
           ListElement { text: "WOMAN";  }
           ListElement { text: "UNISEX"; }
         }
         onAccepted: {
          if (gender.find(currentText) === -1) {
             model.append({text: editText})
             currentIndex = gender.find(editText)
           }
         }
        }

        ComboBox {
           id: sizeCombo
           Layout.preferredWidth: 200
           Layout.preferredHeight: 40
           font.pointSize: 10
           editable: false
           model: ListModel {
            id: mode4
            ListElement { text: "_XS";    }
            ListElement { text: "_S";     }
            ListElement { text: "_M";     }
            ListElement { text: "_L";     }
            ListElement { text: "_XL";    }
            ListElement { text: " _XXL";  }
            ListElement { text: "_80A";   }
            ListElement { text: "_80B";   }
            ListElement { text: "_80C";   }
            ListElement { text: "_80D";   }
            ListElement { text: "_80E";   }
            ListElement { text: "_80F";   }
            ListElement { text: "_85A";   }
            ListElement { text: "_85B";   }
            ListElement { text: "_85C";   }
            ListElement { text: " _85D";  }
            ListElement { text: " _85E";  }
            ListElement { text: "_85F";   }
            ListElement { text: "_90A";   }
            ListElement { text: "_90B";   }
            ListElement { text: "_90C";   }
            ListElement { text: "_90D";   }
            ListElement { text: "_90E";   }
            ListElement { text: "_90F";   }
            ListElement { text: "_95F";   }
            ListElement { text: "_95B";   }
            ListElement { text: "_95C";   }
            ListElement { text: "_95D";   }
            ListElement { text: "_95E";   }
            ListElement { text: "_95F";   }
            ListElement { text: "_100A";  }
            ListElement { text: "_100B";  }
            ListElement { text: "_100C";  }
            ListElement { text: "_100D";  }
            ListElement { text: "_100E";  }
            ListElement { text: "_100F";  }
         }
         onAccepted: {
          if (sizeCombo.find(currentText) === -1) {
             mode4.append({text: editText})
             currentIndex = sizeCombo.find(editText)
           }
         }
        }
        TextField {
            id: sku
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            Layout.fillWidth: true
            placeholderText: "sku"
        }

        ComboBox {
          id: category
          Layout.preferredWidth: 200
          Layout.preferredHeight: 40
          font.pointSize: 10
          editable: false
          model: ListModel {
           id: model3
           ListElement { text: "UNDERWEAR";   }
           ListElement { text: "T_SHIRT";     }
           ListElement { text: "SHIRT";       }
           ListElement { text: "SKIRT";       }
           ListElement { text: "BRA";         }
           ListElement { text: "BRALETTE";    }
           ListElement { text: "TIDDIEFIGGZ"; }
           ListElement { text: "THONG";      }
           ListElement { text: "STRING";      }
           ListElement { text: "G_STRING";    }
           ListElement { text: "BIKINI";      }
           ListElement { text: "BRAZILIAN";   }
           ListElement { text: "CLASSIC";     }
           ListElement { text: "JEANS";       }
           ListElement { text: "BRIEFS";      }
           ListElement { text: "TRUNKS";      }
           ListElement { text: "BOXERS";      }
           ListElement { text: "JOCKSTRAP";   }
         }

         onAccepted: {
          if (category.find(currentText) === -1) {
             model3.append({text: editText})
             currentIndex = category.find(editText)
           }
         }
        }

        Button {
            id: proccessButton
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 15
            text: "CREATE ITEM"
            onClicked: function() {
                backend.newItem(itemName.text,description.text,price.text,colorCombo.currentText,gender.currentText,sizeCombo.currentText,sku.text,category.currentText);
            }
        }
    }
}
