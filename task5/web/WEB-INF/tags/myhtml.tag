<%@ tag description="Main layout" pageEncoding="UTF-8" %>
<%@ tag body-content="scriptless" %>
<%@ attribute name="title" required="true" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<t:header />
<t:menu />

<main class="page-content">
    <div class="container">
        <jsp:doBody/>
    </div>
</main>

<t:footer />

</body>
</html>
