package io.sen.canteenia.email;


import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    public String getResetPasswordTemplate(String email, String token)
    {
        String html = "<!doctype html>\n" +
                "<html lang=\"en-US\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +
                "    <title>Reset Password Email Template</title>\n" +
                "    <meta name=\"description\" content=\"Reset Password Email Template.\">\n" +
                "    <style type=\"text/css\">\n" +
                "        @import url(http://fonts.googleapis.com/css?family=Raleway:700,800);\n" +
                "\n" +
                "        html, body { margin: 0; }\n" +
                "\n" +
                "        :focus { outline: none; }\n" +
                "        ::-webkit-input-placeholder { color: #DEDFDF; }\n" +
                "        ::-moz-placeholder { color: #DEDFDF; }\n" +
                "        :-moz-placeholder { color: #DEDFDF; }\n" +
                "        ::-ms-input-placeholder { color: #DEDFDF; }\n" +
                "\n" +
                "        body {\n" +
                "        background: #6ED0F6;\n" +
                "        color: #fff;\n" +
                "        font-family: 'Raleway', sans-serif;\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        }\n" +
                "\n" +
                "        #wrapper, label, #arrow, button span { transition: all .5s cubic-bezier(.6,0,.4,1); }\n" +
                "\n" +
                "        #wrapper { overflow: hidden; }\n" +
                "\n" +
                "        #signin:checked ~ #wrapper { height: 178px; }\n" +
                "        #signin:checked ~ #wrapper #arrow { left: 32px; }\n" +
                "        #signin:checked ~ button span { transform: translate3d(0,-72px,0); }\n" +
                "\n" +
                "        #signup:checked ~ #wrapper { height: 262px; }\n" +
                "        #signup:checked ~ #wrapper #arrow { left: 137px; }\n" +
                "        #signup:checked ~ button span { transform: translate3d(0,-144px,0); }\n" +
                "\n" +
                "        #reset:checked ~ #wrapper { height: 94px; }\n" +
                "        #reset:checked ~ #wrapper #arrow { left: 404px; }\n" +
                "        #reset:checked ~ button span { transform: translate3d(0,0,0); }\n" +
                "\n" +
                "        form {\n" +
                "        width: 450px;\n" +
                "        height: 370px;\n" +
                "        margin: -185px -225px;\n" +
                "        position: absolute;\n" +
                "        left: 50%;\n" +
                "        top: 50%;\n" +
                "        }\n" +
                "\n" +
                "        input[type=radio] { display: none; }\n" +
                "\n" +
                "        label {\n" +
                "        cursor: pointer;\n" +
                "        display: inline-block;\n" +
                "        font-size: 22px;\n" +
                "        font-weight: 800;\n" +
                "        opacity: .5;\n" +
                "        margin-bottom: 30px;\n" +
                "        text-transform: uppercase;\n" +
                "        }\n" +
                "        label:hover {\n" +
                "        transition: all .3s cubic-bezier(.6,0,.4,1);\n" +
                "        opacity: 1;\n" +
                "        }\n" +
                "        label[for=\"signin\"] { margin-right: 20px; }\n" +
                "        label[for=\"reset\"] { float: right; }\n" +
                "        input[type=radio]:checked + label { opacity: 1; }\n" +
                "\n" +
                "        input[type=text],\n" +
                "        input[type=password] {\n" +
                "        background: #fff;\n" +
                "        border: none;\n" +
                "        border-radius: 8px;\n" +
                "        font-size: 27px;\n" +
                "        font-family: 'Raleway', sans-serif;\n" +
                "        height: 72px;\n" +
                "        width: 99.5%;\n" +
                "        margin-bottom: 10px;\n" +
                "        opacity: 1;\n" +
                "        text-indent: 20px;\n" +
                "        transition: all .2s ease-in-out;\n" +
                "        }\n" +
                "        button {\n" +
                "        background: #079BCF;\n" +
                "        border: none;\n" +
                "        border-radius: 8px;\n" +
                "        color: #fff;\n" +
                "        cursor: pointer;\n" +
                "        font-family: 'Raleway', sans-serif;\n" +
                "        font-size: 27px;\n" +
                "        height: 72px;\n" +
                "        width: 100%;\n" +
                "        margin-bottom: 10px;\n" +
                "        overflow: hidden;\n" +
                "        transition: all .3s cubic-bezier(.6,0,.4,1);\n" +
                "        }\n" +
                "        button span {\n" +
                "        display: block;\n" +
                "        line-height: 72px;\n" +
                "        position: relative;\n" +
                "        top: -2px;\n" +
                "        transform: translate3d(0,0,0);\n" +
                "        }\n" +
                "        button:hover {\n" +
                "        background: #007BA5;\n" +
                "        }\n" +
                "\n" +
                "        #arrow {\n" +
                "        height: 0;\n" +
                "        width: 0;\n" +
                "        border-bottom: 10px solid #fff;\n" +
                "        border-left: 10px solid transparent;\n" +
                "        border-right: 10px solid transparent;\n" +
                "        position: relative;\n" +
                "        left: 32px;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        #hint {\n" +
                "        width: 100%;\n" +
                "        text-align: center;\n" +
                "        position: absolute;\n" +
                "        bottom: 20px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <form id='password-form'>\n" +
                "      <div id=\"wrapper\">\n" +
                "        \n" +
                "        <input id=\"password\" placeholder=\"New Password\" type=\"password\">\n" +
                "        <input id=\"password1\" placeholder=\"Confirm password\" type=\"password\">\n" +
                "      </div>\n" +
                "      <button type=\"submit\" value=\"submit\">\n" +
                "          Reset password\n" +
                "      </button>\n" +
                "    </form>\n" +
                "    <div id=\"hint\">Powered By @ McDA's</div>\n" +
                "</body>\n" +
                "\n" +
                "<script>\n" +
                "    var resetPasssword = async (event) => {\n" +
                "\n" +
                "        event.preventDefault();\n" +
                "        const password = document.getElementById('password').value;\n" +
                "        const password2 = document.getElementById('password1').value;\n" +
                "\n" +
                "        if(password !== password2){\n" +
                "                alert('Passwords do not match,Please try again');\n" +
                "                return;\n" +
                "        }\n" +
                "\n" +
                "        var res = await fetch('/api/auth/reset-password',{\n" +
                "            method: 'POST',\n" +
                "            headers: {\n" +
                "                'Content-Type':'application/json'\n" +
                "            },\n" +
                "            body: JSON.stringify({\n" +
                "                plainUserPassword:password,\n" +
                "                email:\""+ email +"\",\n" +
                "                token:\""+ token +"\"\n" +
                "            })\n" +
                "        });\n" +
                "        var resJson = await res.json();\n" +
                "        console.log(resJson);\n" +
                "        if(resJson.status === 'ok'){\n" +
                "            console.log(\"Password changed successfully!\");\n" +
                "        }\n" +
                "        else{\n" +
                "            console.log(\"An error has occured while changing password!!\");\n" +
                "        }\n" +
                "    }\n" +
                "    const form = document.getElementById('password-form');\n" +
                "    form.addEventListener('submit',resetPasssword);\n" +
                "</script>\n" +
                "\n" +
                "</html>";

        return html;
    }

    public String getPasswordResetEmailTemplate(String username, String url)
    {
        String html = "<!doctype html>\n" +
                "    <html lang=\"en-US\">\n" +
                "\n" +
                "    <head>\n" +
                "        <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n" +
                "        <title>Reset Password</title>\n" +
                "        <meta name=\"description\" content=\"Reset Password Email Template.\">\n" +
                "        <style type=\"text/css\">\n" +
                "            a:hover {text-decoration: underline !important;}\n" +
                "        </style>\n" +
                "    </head>\n" +
                "\n" +
                "    <body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">\n" +
                "        <!--100% body table-->\n" +
                "        <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"\n" +
                "            style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"\n" +
                "                        align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                        <tr>\n" +
                "                            <td style=\"height:80px;\">&nbsp;</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"text-align:center;\">\n" +
                "                            <a href=\"https://github.com/ossdaiict\" title=\"logo\" target=\"_blank\">\n" +
                "                                <img width=\"60\" src=\"https://avatars3.githubusercontent.com/u/16758973?s=200&v=4\" title=\"logo\" alt=\"logo\">\n" +
                "                            </a>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"height:20px;\">&nbsp;</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td>\n" +
                "                                <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                    style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">\n" +
                "                                    <tr>\n" +
                "                                        <td style=\"height:40px;\">&nbsp;</td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td style=\"padding:0 35px;\">\n" +
                "                                            <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">You have\n" +
                "                                                requested to reset your password</h1>\n" +
                "                                            <span\n" +
                "                                                style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>\n" +
                "                                            <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\">   \n" +
                "                                             Hey "+username+",\n" +
                "                                            </p>\n" +
                "                                            <p style=\"color:#455056; font-size:15px;line-height:24px;margin-top:35px;\">\n" +
                "                                                We cannot simply send you your old password. A unique link to reset your\n" +
                "                                                password has been generated for you. To reset your password, click the\n" +
                "                                                following link and follow the instructions.                                            \n" +
                "                                            </p>\n" +
                "                                            <a href=\""+url+"\"\n" +
                "                                                style=\"background:#20e277;text-decoration:none !important; font-weight:500; margin-top:35px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 24px;display:inline-block;border-radius:50px;\">Reset\n" +
                "                                                Password</a>              \n" +
                "                                            <p style=\"color:#455056; font-size:15px;line-height:24px; margin-top:35px;\">\n" +
                "                                                If you don’t use this link within 30 minutes, it will expire. Hope You have a nice experience.\n" +
                "                                            </p>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    <tr>\n" +
                "                                        <td style=\"height:40px;\">&nbsp;</td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </td>\n" +
                "                        <tr>\n" +
                "                            <td style=\"height:20px;\">&nbsp;</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"text-align:center;\">\n" +
                "                                <p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>Powered by McDA's, 2021</strong></p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"height:80px;\">&nbsp;</td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <!--/100% body table-->\n" +
                "    </body>\n" +
                "\n" +
                "</html>";

        return html;
    }

}
