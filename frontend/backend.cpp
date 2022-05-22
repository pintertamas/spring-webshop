#include "backend.h"

Backend::Backend(QObject *parent) : QObject{parent} {

    manager = new QNetworkAccessManager();



    QObject::connect(manager, &QNetworkAccessManager::finished, this, [=](QNetworkReply *res) {

        if (res->url().path() == "/login")
                   LoginResponse(res);
        if (res->url().path() == "/item/list")
                    ItemLoader(res);
        if (res->url().path() == "/cart/list")
                    ItemLoaderCart(res);
    });
}

QVector<Item> Backend::cartItems() {
    return cart_Items;
}

QVector<Item> Backend::items() {
    return shop_Items;
}


void Backend::registration(QString username, QString password, QString email, QString telephone) {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setUrl(QUrl("http://localhost:8080/register"));

    QJsonObject user{
        {"id", ""},
        {"role", "USER"},
        {"username", username},
        {"password", password},
        {"email", email},
        {"telephone", telephone}
    };
    QJsonDocument doc(user);
    QByteArray data = doc.toJson();

    manager->post(request,data);

}

void Backend::login(QString username, QString password) {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader(QByteArray("Authorization"), QByteArray("Token your_token"));
    request.setUrl(QUrl("http://localhost:8080/login"));

    QJsonObject user{
        {"username", username},
        {"password", password},
    };

    QJsonDocument doc(user);
    QByteArray data = doc.toJson();

    manager->post(request,data);

}

void Backend::editUser(QString username, QString email, QString telephone) {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/user/edit/"));

    QJsonObject user{
        {"username", username},
        {"username", username},
        {"email", email},
        {"telephone", telephone},
    };

    QJsonDocument doc(user);
    QByteArray data = doc.toJson();

    manager->post(request,data);
}

void Backend::logout() {
    auto request = QNetworkRequest();
    token = QString();
    manager->post(request, QByteArray());

}


void Backend::addtoCart(Item clothItem) {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl(("http://localhost:8080/cart/add/" + QString::number(clothItem.id))));

    QJsonObject user{
        {"id", clothItem.id},
    };
    QJsonDocument doc(user);
    QByteArray data = doc.toJson();

    manager->post(request,data);
    emit this-> loadCart();
}

void Backend::removefromCart(Item clothItem) {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/cart/remove/" + QString::number(clothItem.id)));

    manager->deleteResource(request);
    emit this->cartItems();
}

void Backend::newItem(QString name, QString description, QString price, QString color, QString gender, QString size, QString sku, QString category){
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/item/create"));

    QJsonArray as = {"sd", "asd"};

    QJsonObject item{
        {"name", name},
        {"description", description},
        {"price", price.toDouble()},
        {"images", as},
        {"color", color},
        {"gender", gender},
        {"category", category},
        {"size", size},
        {"sku", sku.toInt()}
    };

    QJsonDocument doc(item);
    QByteArray data = doc.toJson();

    manager->post(request,data);
    emit this->items();
}

void Backend::editItem(Item editItem) {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/item/edit/" + QString::number(editItem.id)));

    QJsonObject item{
        {"id", editItem.id},
        {"name", editItem.name},
        {"description", editItem.description},
        {"price", editItem.price},
        {"color", editItem.color},
        {"gender", editItem.gender},
        {"size", editItem.size},
        {"sku", editItem.sku},
        {"category", editItem.category},
        {"discount", editItem.discount}
    };

    QJsonDocument doc(item);
    QByteArray data = doc.toJson();

    manager->put(request,data);
}

void Backend::deleteItem(Item clothItem) {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/item/delete/" + QString::number(clothItem.id)));

    manager->deleteResource(request);
}


void Backend::createOrder() {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/order/create"));

    manager->post(request,QByteArray());
}


void Backend::LoginResponse(QNetworkReply *res) {
    QJsonDocument doc = QJsonDocument::fromJson(res->readAll());
    QJsonObject JON = doc.object();
    token =JON.value("jwtToken").toString();
    emit this->loadItem();
    emit this->loadCart();
}

void Backend::ItemLoader(QNetworkReply *res) {

    shop_Items.clear();
    QJsonDocument doc = QJsonDocument::fromJson(res->readAll());
    QJsonArray jsonArray = doc.array();

     for (auto element2 : jsonArray) {
        QJsonObject  element = element2.toObject();

        Item item;
        item.id = element.value("id").toInt();
        item.name = element.value("name").toString();
        item.description = element.value("description").toString();
        item.color = element.value("color").toString();
        item.price = element.value("price").toDouble();
        item.gender = element.value("gender").toString();
        item.size = element.value("size").toString();
        item.sku = element.value("sku").toInt();
        item.category = element.value("category").toString();
        item.discount = element.value("discount").toString();
        item.carts = element.value("carts").toString();

        shop_Items.push_back(item);

     }
}

void Backend::ItemLoaderCart(QNetworkReply *res) {
    cart_Items.clear();
    QJsonDocument doc = QJsonDocument::fromJson(res->readAll());
    QJsonArray jsonArray = doc.array();

     for (auto element2 : jsonArray) {
        QJsonObject  element = element2.toObject();

        Item item;
        item.id = element.value("id").toInt();
        item.name = element.value("name").toString();
        item.description = element.value("description").toString();
        item.price = element.value("price").toDouble();
        item.color = element.value("color").toString();
        item.gender = element.value("gender").toString();
        item.size = element.value("size").toString();
        item.sku = element.value("sku").toInt();
        item.category = element.value("category").toString();
        item.discount = element.value("discount").toString();
        item.carts = element.value("carts").toString();

        cart_Items.push_back(item);

     }
}

void Backend::loadCart() {
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/cart/list"));

    manager->get(request);
}

void Backend::loadItem(){
    auto request = QNetworkRequest();
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");
    request.setRawHeader("Authorization", (QString("Bearer ") + token).toLocal8Bit());
    request.setUrl(QUrl("http://localhost:8080/item/list"));

    manager->get(request);
}
