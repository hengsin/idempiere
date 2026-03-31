# iDempiere SOAP Web Services (ADInterface)

**Summary:** The SOAP Web Services engine for iDempiere, providing a standardized XML and JSON interface (ADInterface) to interact with the iDempiere Active Data Dictionary. It supports CRUD operations on any table, process execution, and workflow management.

## 🚀 Features

* **ADInterface Support:** Provides a unified service layer for both SOAP and RESTful communication.
* **Model Access (CRUD):** Generic `createData`, `readData`, `updateData`, and `deleteData` operations for any table defined in the Application Dictionary.
* **Process Execution:** Trigger any iDempiere process or report remotely via `runProcess`.
* **Workflow Integration:** Manage workflow activities and responses through web service calls.
* **Dual Format:** Full support for both XML and JSON payloads.
* **Dictionary Driven:** Automatically respects all security, validation, and access rules defined within the iDempiere UI.

## ⚙️ Compatibility

* **Core Module**
* **Java Version:** 17+
* **Protocol:** SOAP 1.1/1.2 and REST (POST-based)

## 📦 Database Changes

* **Core Tables:** Uses `WS_WebServiceType`, `WS_WebServiceMethod`, and `WS_WebServiceTypeAccess` to manage external access.
* **Metadata:** Configuration is stored in the standard iDempiere schema; no additional DDL is required as this is a core component.

## 🛠 Usage & Configuration

* **Endpoint:** Typically accessible at `https://[your-server]/ADInterface/services/rest/model_adservice/` for REST or via the WSDL for SOAP.
* **Security:**
    * Access is controlled via the **Web Service Type** window.
    * Users must have a role with `RoleType` set to `Web Service` or `None`.
    * Every request must include an `ADLoginRequest` object containing credentials, Client ID, Org ID, and Role ID.
* **Testing:** You can use the `QueryData` method to retrieve records using standard SQL WHERE clauses (e.g., `Name LIKE 'A%'`).

## 👤 Author / Support

* **Developer:** iDempiere Community
* **Source Code:** [https://github.com/idempiere/idempiere/tree/master/org.idempiere.webservices](https://github.com/idempiere/idempiere/tree/master/org.idempiere.webservices)
* **Documentation:** [iDempiere Wiki - Web Services](https://wiki.idempiere.org/en/Web_services)