/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takamakachain.walletweblauncher.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.NotificationBroadcasterSupport;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.ignite.internal.util.IgniteUtils;
import org.json.JSONObject;

/**
 *
 * @author isacco
 */
public class CommandTakamakaWebApp extends AbstractGenericCommand {

    String manual = null;

    private static Path webapp;
    private static Path jdkPath;
    private Options opt;
    private DefaultParser par;
    private CommandLine cmd;
    private String suffix, arch;
    private static JSONObject jsonIntegrity;
    private static Path libraryJdkPath;
    private static String jdkHash;
    private static Path libraryPayaraPath;
    private static String payaraHash;
    private static Path libraryWebAppPath;
    private static String webAppHash;
    private static final String license = "Attribution-NoDerivatives 4.0 International\n" +
"\n" +
"==========================================\n" +
"\n" +
"Commons Corporation (\"Creative Commons\") is not a law firm and\n" +
"does not provide legal services or legal advice. Distribution of\n" +
"Creative Commons public licenses does not create a lawyer-client or\n" +
"other relationship. Creative Commons makes its licenses and related\n" +
"information available on an \"as-is\" basis. Creative Commons gives no\n" +
"warranties regarding its licenses, any material licensed under their\n" +
"terms and conditions, or any related information. Creative Commons\n" +
"disclaims all liability for damages resulting from their use to the\n" +
"fullest extent possible.\n" +
"\n" +
"Using Creative Commons Public Licenses\n" +
"\n" +
"Creative Commons public licenses provide a standard set of terms and\n" +
"conditions that creators and other rights holders may use to share\n" +
"original works of authorship and other material subject to copyright\n" +
"and certain other rights specified in the public license below. The\n" +
"following considerations are for informational purposes only, are not\n" +
"exhaustive, and do not form part of our licenses.\n" +
"\n" +
"     Considerations for licensors: Our public licenses are\n" +
"     intended for use by those authorized to give the public\n" +
"     permission to use material in ways otherwise restricted by\n" +
"     copyright and certain other rights. Our licenses are\n" +
"     irrevocable. Licensors should read and understand the terms\n" +
"     and conditions of the license they choose before applying it.\n" +
"     Licensors should also secure all rights necessary before\n" +
"     applying our licenses so that the public can reuse the\n" +
"     material as expected. Licensors should clearly mark any\n" +
"     material not subject to the license. This includes other CC-\n" +
"     licensed material, or material used under an exception or\n" +
"     limitation to copyright. More considerations for licensors:\n" +
"    wiki.creativecommons.org/Considerations_for_licensors\n" +
"\n" +
"     Considerations for the public: By using one of our public\n" +
"     licenses, a licensor grants the public permission to use the\n" +
"     licensed material under specified terms and conditions. If\n" +
"     the licensor's permission is not necessary for any reason--for\n" +
"     example, because of any applicable exception or limitation to\n" +
"     copyright--then that use is not regulated by the license. Our\n" +
"     licenses grant only permissions under copyright and certain\n" +
"     other rights that a licensor has authority to grant. Use of\n" +
"     the licensed material may still be restricted for other\n" +
"     reasons, including because others have copyright or other\n" +
"     rights in the material. A licensor may make special requests,\n" +
"     such as asking that all changes be marked or described.\n" +
"     Although not required by our licenses, you are encouraged to\n" +
"     respect those requests where reasonable. More considerations\n" +
"     for the public:\n" +
"    wiki.creativecommons.org/Considerations_for_licensees\n" +
"\n" +
"\n" +
"==========================================\n" +
"\n" +
"Commons Attribution-NoDerivatives 4.0 International Public\n" +
"License\n" +
"\n" +
"By exercising the Licensed Rights (defined below), You accept and agree\n" +
"to be bound by the terms and conditions of this Creative Commons\n" +
"Attribution-NoDerivatives 4.0 International Public License (\"Public\n" +
"License\"). To the extent this Public License may be interpreted as a\n" +
"contract, You are granted the Licensed Rights in consideration of Your\n" +
"acceptance of these terms and conditions, and the Licensor grants You\n" +
"such rights in consideration of benefits the Licensor receives from\n" +
"making the Licensed Material available under these terms and\n" +
"conditions.\n" +
"\n" +
"\n" +
"Section 1 -- Definitions.\n" +
"\n" +
"  a. Adapted Material means material subject to Copyright and Similar\n" +
"     Rights that is derived from or based upon the Licensed Material\n" +
"     and in which the Licensed Material is translated, altered,\n" +
"     arranged, transformed, or otherwise modified in a manner requiring\n" +
"     permission under the Copyright and Similar Rights held by the\n" +
"     Licensor. For purposes of this Public License, where the Licensed\n" +
"     Material is a musical work, performance, or sound recording,\n" +
"     Adapted Material is always produced where the Licensed Material is\n" +
"     synched in timed relation with a moving image.\n" +
"\n" +
"  b. Copyright and Similar Rights means copyright and/or similar rights\n" +
"     closely related to copyright including, without limitation,\n" +
"     performance, broadcast, sound recording, and Sui Generis Database\n" +
"     Rights, without regard to how the rights are labeled or\n" +
"     categorized. For purposes of this Public License, the rights\n" +
"     specified in Section 2(b)(1)-(2) are not Copyright and Similar\n" +
"     Rights.\n" +
"\n" +
"  c. Effective Technological Measures means those measures that, in the\n" +
"     absence of proper authority, may not be circumvented under laws\n" +
"     fulfilling obligations under Article 11 of the WIPO Copyright\n" +
"     Treaty adopted on December 20, 1996, and/or similar international\n" +
"     agreements.\n" +
"\n" +
"  d. Exceptions and Limitations means fair use, fair dealing, and/or\n" +
"     any other exception or limitation to Copyright and Similar Rights\n" +
"     that applies to Your use of the Licensed Material.\n" +
"\n" +
"  e. Licensed Material means the artistic or literary work, database,\n" +
"     or other material to which the Licensor applied this Public\n" +
"     License.\n" +
"\n" +
"  f. Licensed Rights means the rights granted to You subject to the\n" +
"     terms and conditions of this Public License, which are limited to\n" +
"     all Copyright and Similar Rights that apply to Your use of the\n" +
"     Licensed Material and that the Licensor has authority to license.\n" +
"\n" +
"  g. Licensor means the individual(s) or entity(ies) granting rights\n" +
"     under this Public License.\n" +
"\n" +
"  h. Share means to provide material to the public by any means or\n" +
"     process that requires permission under the Licensed Rights, such\n" +
"     as reproduction, public display, public performance, distribution,\n" +
"     dissemination, communication, or importation, and to make material\n" +
"     available to the public including in ways that members of the\n" +
"     public may access the material from a place and at a time\n" +
"     individually chosen by them.\n" +
"\n" +
"  i. Sui Generis Database Rights means rights other than copyright\n" +
"     resulting from Directive 96/9/EC of the European Parliament and of\n" +
"     the Council of 11 March 1996 on the legal protection of databases,\n" +
"     as amended and/or succeeded, as well as other essentially\n" +
"     equivalent rights anywhere in the world.\n" +
"\n" +
"  j. You means the individual or entity exercising the Licensed Rights\n" +
"     under this Public License. Your has a corresponding meaning.\n" +
"\n" +
"\n" +
"Section 2 -- Scope.\n" +
"\n" +
"  a. License grant.\n" +
"\n" +
"       1. Subject to the terms and conditions of this Public License,\n" +
"          the Licensor hereby grants You a worldwide, royalty-free,\n" +
"          non-sublicensable, non-exclusive, irrevocable license to\n" +
"          exercise the Licensed Rights in the Licensed Material to:\n" +
"\n" +
"            a. reproduce and Share the Licensed Material, in whole or\n" +
"               in part; and\n" +
"\n" +
"            b. produce and reproduce, but not Share, Adapted Material.\n" +
"\n" +
"       2. Exceptions and Limitations. For the avoidance of doubt, where\n" +
"          Exceptions and Limitations apply to Your use, this Public\n" +
"          License does not apply, and You do not need to comply with\n" +
"          its terms and conditions.\n" +
"\n" +
"       3. Term. The term of this Public License is specified in Section\n" +
"          6(a).\n" +
"\n" +
"       4. Media and formats; technical modifications allowed. The\n" +
"          Licensor authorizes You to exercise the Licensed Rights in\n" +
"          all media and formats whether now known or hereafter created,\n" +
"          and to make technical modifications necessary to do so. The\n" +
"          Licensor waives and/or agrees not to assert any right or\n" +
"          authority to forbid You from making technical modifications\n" +
"          necessary to exercise the Licensed Rights, including\n" +
"          technical modifications necessary to circumvent Effective\n" +
"          Technological Measures. For purposes of this Public License,\n" +
"          simply making modifications authorized by this Section 2(a)\n" +
"          (4) never produces Adapted Material.\n" +
"\n" +
"       5. Downstream recipients.\n" +
"\n" +
"            a. Offer from the Licensor -- Licensed Material. Every\n" +
"               recipient of the Licensed Material automatically\n" +
"               receives an offer from the Licensor to exercise the\n" +
"               Licensed Rights under the terms and conditions of this\n" +
"               Public License.\n" +
"\n" +
"            b. No downstream restrictions. You may not offer or impose\n" +
"               any additional or different terms or conditions on, or\n" +
"               apply any Effective Technological Measures to, the\n" +
"               Licensed Material if doing so restricts exercise of the\n" +
"               Licensed Rights by any recipient of the Licensed\n" +
"               Material.\n" +
"\n" +
"       6. No endorsement. Nothing in this Public License constitutes or\n" +
"          may be construed as permission to assert or imply that You\n" +
"          are, or that Your use of the Licensed Material is, connected\n" +
"          with, or sponsored, endorsed, or granted official status by,\n" +
"          the Licensor or others designated to receive attribution as\n" +
"          provided in Section 3(a)(1)(A)(i).\n" +
"\n" +
"  b. Other rights.\n" +
"\n" +
"       1. Moral rights, such as the right of integrity, are not\n" +
"          licensed under this Public License, nor are publicity,\n" +
"          privacy, and/or other similar personality rights; however, to\n" +
"          the extent possible, the Licensor waives and/or agrees not to\n" +
"          assert any such rights held by the Licensor to the limited\n" +
"          extent necessary to allow You to exercise the Licensed\n" +
"          Rights, but not otherwise.\n" +
"\n" +
"       2. Patent and trademark rights are not licensed under this\n" +
"          Public License.\n" +
"\n" +
"       3. To the extent possible, the Licensor waives any right to\n" +
"          collect royalties from You for the exercise of the Licensed\n" +
"          Rights, whether directly or through a collecting society\n" +
"          under any voluntary or waivable statutory or compulsory\n" +
"          licensing scheme. In all other cases the Licensor expressly\n" +
"          reserves any right to collect such royalties.\n" +
"\n" +
"\n" +
"Section 3 -- License Conditions.\n" +
"\n" +
"Your exercise of the Licensed Rights is expressly made subject to the\n" +
"following conditions.\n" +
"\n" +
"  a. Attribution.\n" +
"\n" +
"       1. If You Share the Licensed Material, You must:\n" +
"\n" +
"            a. retain the following if it is supplied by the Licensor\n" +
"               with the Licensed Material:\n" +
"\n" +
"                 i. identification of the creator(s) of the Licensed\n" +
"                    Material and any others designated to receive\n" +
"                    attribution, in any reasonable manner requested by\n" +
"                    the Licensor (including by pseudonym if\n" +
"                    designated);\n" +
"\n" +
"                ii. a copyright notice;\n" +
"\n" +
"               iii. a notice that refers to this Public License;\n" +
"\n" +
"                iv. a notice that refers to the disclaimer of\n" +
"                    warranties;\n" +
"\n" +
"                 v. a URI or hyperlink to the Licensed Material to the\n" +
"                    extent reasonably practicable;\n" +
"\n" +
"            b. indicate if You modified the Licensed Material and\n" +
"               retain an indication of any previous modifications; and\n" +
"\n" +
"            c. indicate the Licensed Material is licensed under this\n" +
"               Public License, and include the text of, or the URI or\n" +
"               hyperlink to, this Public License.\n" +
"\n" +
"          For the avoidance of doubt, You do not have permission under\n" +
"          this Public License to Share Adapted Material.\n" +
"\n" +
"       2. You may satisfy the conditions in Section 3(a)(1) in any\n" +
"          reasonable manner based on the medium, means, and context in\n" +
"          which You Share the Licensed Material. For example, it may be\n" +
"          reasonable to satisfy the conditions by providing a URI or\n" +
"          hyperlink to a resource that includes the required\n" +
"          information.\n" +
"\n" +
"       3. If requested by the Licensor, You must remove any of the\n" +
"          information required by Section 3(a)(1)(A) to the extent\n" +
"          reasonably practicable.\n" +
"\n" +
"\n" +
"Section 4 -- Sui Generis Database Rights.\n" +
"\n" +
"Where the Licensed Rights include Sui Generis Database Rights that\n" +
"apply to Your use of the Licensed Material:\n" +
"\n" +
"  a. for the avoidance of doubt, Section 2(a)(1) grants You the right\n" +
"     to extract, reuse, reproduce, and Share all or a substantial\n" +
"     portion of the contents of the database, provided You do not Share\n" +
"     Adapted Material;\n" +
"  b. if You include all or a substantial portion of the database\n" +
"     contents in a database in which You have Sui Generis Database\n" +
"     Rights, then the database in which You have Sui Generis Database\n" +
"     Rights (but not its individual contents) is Adapted Material; and\n" +
"  c. You must comply with the conditions in Section 3(a) if You Share\n" +
"     all or a substantial portion of the contents of the database.\n" +
"\n" +
"For the avoidance of doubt, this Section 4 supplements and does not\n" +
"replace Your obligations under this Public License where the Licensed\n" +
"Rights include other Copyright and Similar Rights.\n" +
"\n" +
"\n" +
"Section 5 -- Disclaimer of Warranties and Limitation of Liability.\n" +
"\n" +
"  a. UNLESS OTHERWISE SEPARATELY UNDERTAKEN BY THE LICENSOR, TO THE\n" +
"     EXTENT POSSIBLE, THE LICENSOR OFFERS THE LICENSED MATERIAL AS-IS\n" +
"     AND AS-AVAILABLE, AND MAKES NO REPRESENTATIONS OR WARRANTIES OF\n" +
"     ANY KIND CONCERNING THE LICENSED MATERIAL, WHETHER EXPRESS,\n" +
"     IMPLIED, STATUTORY, OR OTHER. THIS INCLUDES, WITHOUT LIMITATION,\n" +
"     WARRANTIES OF TITLE, MERCHANTABILITY, FITNESS FOR A PARTICULAR\n" +
"     PURPOSE, NON-INFRINGEMENT, ABSENCE OF LATENT OR OTHER DEFECTS,\n" +
"     ACCURACY, OR THE PRESENCE OR ABSENCE OF ERRORS, WHETHER OR NOT\n" +
"     KNOWN OR DISCOVERABLE. WHERE DISCLAIMERS OF WARRANTIES ARE NOT\n" +
"     ALLOWED IN FULL OR IN PART, THIS DISCLAIMER MAY NOT APPLY TO YOU.\n" +
"\n" +
"  b. TO THE EXTENT POSSIBLE, IN NO EVENT WILL THE LICENSOR BE LIABLE\n" +
"     TO YOU ON ANY LEGAL THEORY (INCLUDING, WITHOUT LIMITATION,\n" +
"     NEGLIGENCE) OR OTHERWISE FOR ANY DIRECT, SPECIAL, INDIRECT,\n" +
"     INCIDENTAL, CONSEQUENTIAL, PUNITIVE, EXEMPLARY, OR OTHER LOSSES,\n" +
"     COSTS, EXPENSES, OR DAMAGES ARISING OUT OF THIS PUBLIC LICENSE OR\n" +
"     USE OF THE LICENSED MATERIAL, EVEN IF THE LICENSOR HAS BEEN\n" +
"     ADVISED OF THE POSSIBILITY OF SUCH LOSSES, COSTS, EXPENSES, OR\n" +
"     DAMAGES. WHERE A LIMITATION OF LIABILITY IS NOT ALLOWED IN FULL OR\n" +
"     IN PART, THIS LIMITATION MAY NOT APPLY TO YOU.\n" +
"\n" +
"  c. The disclaimer of warranties and limitation of liability provided\n" +
"     above shall be interpreted in a manner that, to the extent\n" +
"     possible, most closely approximates an absolute disclaimer and\n" +
"     waiver of all liability.\n" +
"\n" +
"\n" +
"Section 6 -- Term and Termination.\n" +
"\n" +
"  a. This Public License applies for the term of the Copyright and\n" +
"     Similar Rights licensed here. However, if You fail to comply with\n" +
"     this Public License, then Your rights under this Public License\n" +
"     terminate automatically.\n" +
"\n" +
"  b. Where Your right to use the Licensed Material has terminated under\n" +
"     Section 6(a), it reinstates:\n" +
"\n" +
"       1. automatically as of the date the violation is cured, provided\n" +
"          it is cured within 30 days of Your discovery of the\n" +
"          violation; or\n" +
"\n" +
"       2. upon express reinstatement by the Licensor.\n" +
"\n" +
"     For the avoidance of doubt, this Section 6(b) does not affect any\n" +
"     right the Licensor may have to seek remedies for Your violations\n" +
"     of this Public License.\n" +
"\n" +
"  c. For the avoidance of doubt, the Licensor may also offer the\n" +
"     Licensed Material under separate terms or conditions or stop\n" +
"     distributing the Licensed Material at any time; however, doing so\n" +
"     will not terminate this Public License.\n" +
"\n" +
"  d. Sections 1, 5, 6, 7, and 8 survive termination of this Public\n" +
"     License.\n" +
"\n" +
"\n" +
"Section 7 -- Other Terms and Conditions.\n" +
"\n" +
"  a. The Licensor shall not be bound by any additional or different\n" +
"     terms or conditions communicated by You unless expressly agreed.\n" +
"\n" +
"  b. Any arrangements, understandings, or agreements regarding the\n" +
"     Licensed Material not stated herein are separate from and\n" +
"     independent of the terms and conditions of this Public License.\n" +
"\n" +
"\n" +
"Section 8 -- Interpretation.\n" +
"\n" +
"  a. For the avoidance of doubt, this Public License does not, and\n" +
"     shall not be interpreted to, reduce, limit, restrict, or impose\n" +
"     conditions on any use of the Licensed Material that could lawfully\n" +
"     be made without permission under this Public License.\n" +
"\n" +
"  b. To the extent possible, if any provision of this Public License is\n" +
"     deemed unenforceable, it shall be automatically reformed to the\n" +
"     minimum extent necessary to make it enforceable. If the provision\n" +
"     cannot be reformed, it shall be severed from this Public License\n" +
"     without affecting the enforceability of the remaining terms and\n" +
"     conditions.\n" +
"\n" +
"  c. No term or condition of this Public License will be waived and no\n" +
"     failure to comply consented to unless expressly agreed to by the\n" +
"     Licensor.\n" +
"\n" +
"  d. Nothing in this Public License constitutes or may be interpreted\n" +
"     as a limitation upon, or waiver of, any privileges and immunities\n" +
"     that apply to the Licensor or You, including from the legal\n" +
"     processes of any jurisdiction or authority.\n" +
"\n" +
"==========================================\n" +
"\n" +
"Commons is not a party to its public\n" +
"licenses. Notwithstanding, Creative Commons may elect to apply one of\n" +
"its public licenses to material it publishes and in those instances\n" +
"will be considered the “Licensor.” The text of the Creative Commons\n" +
"public licenses is dedicated to the public domain under the CC0 Public\n" +
"Domain Dedication. Except for the limited purpose of indicating that\n" +
"material is shared under a Creative Commons public license or as\n" +
"otherwise permitted by the Creative Commons policies published at\n" +
"creativecommons.org/policies, Creative Commons does not authorize the\n" +
"use of the trademark \"Creative Commons\" or any other trademark or logo\n" +
"of Creative Commons without its prior written consent including,\n" +
"without limitation, in connection with any unauthorized modifications\n" +
"to any of its public licenses or any other arrangements,\n" +
"understandings, or agreements concerning use of licensed material. For\n" +
"the avoidance of doubt, this paragraph does not form part of the\n" +
"public licenses.\n" +
"\n" +
"Creative Commons may be contacted at creativecommons.org.Data protection declaration\n" +
"March 2020\n" +
"Revision 0.1\n" +
"0\n" +
"AiliA SA\n" +
"Weinberghöhe 27\n" +
"6300 Zug - CH\n" +
"mail: info@takamaka.io\n" +
"GENERAL ASPECTS AND SCOPE OF VALIDITY 2\n" +
"WHO IS RESPONSIBLE FOR DATA PROCESSING AND HOW TO CONTACT US? 2\n" +
"WHAT PERSONAL DATA DO WE PROCESS? 2\n" +
"FOR WHAT PURPOSES AND ON WHAT LEGAL BASIS DO WE TREAT USERS' PERSONAL\n" +
"DATA? 3\n" +
"TO WHOM DO WE COMMUNICATE THE PERSONAL DATA OF THE USERS? 3\n" +
"HOW DO WE USE PROFILING AND AUTOMATED DECISION-MAKING RELATED TO\n" +
"USERS? 4\n" +
"WHERE ARE PERSONAL DATA PROCESSED? 4\n" +
"DOES AILIA SA UTILIZE COOKIES OR SIMILAR TECHNOLOGIES? 4\n" +
"HOW ARE USERS' PERSONAL DATA PROTECTED? 5\n" +
"HOW LONG ARE USERS' PERSONAL DATA STORED? 5\n" +
"WHAT RIGHTS DOES THE USER HAVE IN RELATION TO HIS / HER PERSONAL DATA? 5\n" +
"DECLARATION OF CONSENT 6\n" +
"AiliA\n" +
"\n" +
"AiliA SA\n" +
"Weinberghöhe 27\n" +
"6300 Zug - CH\n" +
"mail: info@takamaka.io\n" +
"GENERAL ASPECTS AND SCOPE OF VALIDITY\n" +
"This information provides guidance on the processing of personal data of users. For the\n" +
"provision of our service we treat users' personal data in compliance with the European\n" +
"Data Protection Regulation (GDPR) and the Federal Data Protection Act (LPD).\n" +
"This data protection declaration is valid for the websites, applications, services, contracts\n" +
"and tools developed by AiliA SA, provided that, in the individual case, there are no\n" +
"separate data protection provisions specified and disregarding the way in which the user\n" +
"accesses and uses these services (including access from mobile devices). If necessary we\n" +
"can set up a separate data protection notice for other activities.\n" +
"WHO IS RESPONSIBLE FOR DATA PROCESSING AND\n" +
"HOW TO CONTACT US?\n" +
"The data processing manager is: AiliA SA - Weinberghöhe 27, 6300 Zug, CH.\n" +
"For questions regarding the processing of personal data, please contact the following\n" +
"address: privacy@takamaka.io\n" +
"WHAT PERSONAL DATA DO WE PROCESS?\n" +
"We process the personal data made available by the users themselves. If necessary for the\n" +
"provision of our service, we also process the personal data that we have legitimately\n" +
"obtained from publicly accessible sources (eg public databases, Internet, press) or from\n" +
"third parties (eg general agencies, brokers, affiliated partners, companies solvency\n" +
"verification, private databases).\n" +
"Among others, we may process the following personal data:\n" +
"❏ personal data (eg name, address and other contact details, date of birth, language\n" +
"preferences, proposal and usage data, solvency data)\n" +
"❏ data on customer activities (eg contract data, customer contacts, session data\n" +
"related to visits to our website, apps for mobile devices, participation in contest or\n" +
"offers)\n" +
"❏ other data that we are legally required or authorized to register and process and\n" +
"which we need for authentication and user identification or for verification of the\n" +
"data collected by us (eg the origin of assets)\n" +
"Where, for the conclusion of the contract or for its execution, we need personal data\n" +
"worthy of particular protection, we require the user's consent. Please note that in the\n" +
"AiliA\n" +
"\n" +
"AiliA SA\n" +
"Weinberghöhe 27\n" +
"6300 Zug - CH\n" +
"mail: info@takamaka.io\n" +
"absence of the users' personal data, we are unable to provide our services nor to conclude\n" +
"or manage a contract.\n" +
"FOR WHAT PURPOSES AND ON WHAT LEGAL BASIS DO\n" +
"WE TREAT USERS' PERSONAL DATA?\n" +
"Personal data is used in compliance with the legal provisions for the purposes listed\n" +
"below:\n" +
"❏ To protect our legitimate interests or those of third parties: eg. recognition of\n" +
"fraudulent actions, KYC, AML, analysis of customer relationships and customer\n" +
"behavior for the optimization of contracts and the development of products /\n" +
"services / processes, advertising (incl. profiling) for our products and those of our\n" +
"companies affiliates, newsletters, market and opinion polls.\n" +
"❏ For the conclusion and execution of the contract: for example consultancy and\n" +
"assistance, verification of solvency, management and adjustment of contracts,\n" +
"collection, customer satisfaction surveys relating to contractual services.\n" +
"❏ Based on user consent: in some cases, your consent is required to process user\n" +
"data, e.g. for the processing of particularly sensitive data for the purpose of\n" +
"concluding the contract.\n" +
"❏ Based on legal obligations: we are subject to various legal requirements (eg\n" +
"Money Laundering Act, Supervisory Law and FINMA's legal requirements,\n" +
"retention obligations).\n" +
"TO WHOM DO WE COMMUNICATE THE PERSONAL\n" +
"DATA OF THE USERS?\n" +
"We do not communicate the personal data of the users to unauthorized third parties. Our\n" +
"employees have access only to the data they need to fulfill legal and contractual\n" +
"obligations. For the supply of our services it is also necessary to forward the data both\n" +
"inside and outside AiliA sagl. Depending on the purpose, the data can be forwarded to\n" +
"partners and other companies in the fields of IT services, logistics, printing, collection and\n" +
"marketing.\n" +
"All the third parties (recipients) integrated in our business structure process users'\n" +
"personal data on our behalf and exclusively in the way we are authorized to do so. These\n" +
"third parties are carefully controlled by us in relation to the protection and security of data\n" +
"and, taking into account the applicable legal provisions on the subject of data protection,\n" +
"bound by secrecy and compliance with the provisions on privacy. Furthermore, if we have\n" +
"AiliA\n" +
"\n" +
"AiliA SA\n" +
"Weinberghöhe 27\n" +
"6300 Zug - CH\n" +
"mail: info@takamaka.io\n" +
"a legal obligation, we are required to disclose the personal data of users to government\n" +
"offices (eg authorities, social insurers, courts).\n" +
"HOW DO WE USE PROFILING AND AUTOMATED\n" +
"DECISION-MAKING RELATED TO USERS?\n" +
"User data can be partly processed in an automated manner in order to evaluate certain\n" +
"personal aspects, eg. in the following cases:\n" +
"❏ on the legal and regulatory requirements basis we are obliged to take certain\n" +
"measures (eg combating money laundering and terrorist financing). These\n" +
"measures include data analysis (eg at the conclusion of the contract, during the\n" +
"contractual validity and in the case of payments).\n" +
"❏ we analyze the data to be able to provide customers with information and advice\n" +
"on products. This allows us to modulate our communication and publicity\n" +
"activities on their needs (including market research and opinion polls).\n" +
"WHERE ARE PERSONAL DATA PROCESSED?\n" +
"If necessary for the provision of our services and taking into account the purpose, the\n" +
"personal data of the users are communicated to the recipients listed in point 5 inside and\n" +
"outside Switzerland and the EU. Data transfer in countries outside the EU occurs only if\n" +
"there is adequate data protection comparable to that of Switzerland and the EU or if the\n" +
"recipient contractually ensures equivalent data protection.\n" +
"Data transmission to other companies collaborating with AiliA SA outside Switzerland or\n" +
"the EU is based on AiliA SA data protection standards, which guarantee adequate data\n" +
"protection and are binding for all companies. The list of all the companies that collaborate\n" +
"with AiliA SA and that guarantees those standards can be found at www.takamaka.io\n" +
"DOES AILIA SA UTILIZE COOKIES OR SIMILAR\n" +
"TECHNOLOGIES?\n" +
"When the user visits a website, he/she can retrieve information from the browser and save\n" +
"it, generally in the form of a cookie. This can be information about the user, the settings\n" +
"used or the device used. Generally cookies are used to guarantee the full functionality of\n" +
"AiliA\n" +
"\n" +
"AiliA SA\n" +
"Weinberghöhe 27\n" +
"6300 Zug - CH\n" +
"mail: info@takamaka.io\n" +
"the website. While not allowing the user to be directly identified, they nevertheless offer a\n" +
"personalized web experience.\n" +
"The user can decide not to admit certain types of cookies. The chat function, where\n" +
"available in the contact options, allows the user to connect to a customer service person\n" +
"with whom to communicate in real time in a separate browser window. The use of this\n" +
"function does not require any contact data and the chat contents are deleted at the end\n" +
"of the conversation.\n" +
"HOW ARE USERS' PERSONAL DATA PROTECTED?\n" +
"When the user transfers information through the Internet, or other electronic means,\n" +
"there is always the risk that these will be lost, damaged or manipulated. The user\n" +
"therefore carries out this transmission at his own risk. The transmission of data through\n" +
"the Internet platforms available is generally in encrypted mode. Through appropriate\n" +
"technical and organizational measures we ensure that personal data received are\n" +
"protected from unauthorized access, loss or destruction.\n" +
"HOW LONG ARE USERS' PERSONAL DATA STORED?\n" +
"We treat and save users' personal data for and only for the mandatory period required by\n" +
"legal or contractual provisions.\n" +
"WHAT RIGHTS DOES THE USER HAVE IN RELATION TO\n" +
"HIS / HER PERSONAL DATA?\n" +
"The user has the right to access, rectify, oppose, cancel the personal data and - if\n" +
"applicable - has the right to the portability of data as the right to lodge a complaint with\n" +
"the supervisory authority for the competent data protection. The user also has the right to\n" +
"oppose the processing of personal data for direct marketing purposes. The user has the\n" +
"right to object to the processing of personal data for reasons connected to his particular\n" +
"situation even when the processing takes place within the framework of legitimate\n" +
"interests.\n" +
"To exercise these rights, we invite you to contact the data protection officer.\n" +
"Please note that, following the exercise of these rights, we may no longer be able to\n" +
"conclude and manage the contract or offer additional services. Finally, we remind you\n" +
"that, under certain circumstances and in compliance with current law, we are authorized\n" +
"AiliA\n" +
"\n" +
"AiliA SA\n" +
"Weinberghöhe 27\n" +
"6300 Zug - CH\n" +
"mail: info@takamaka.io\n" +
"to refuse or only partially grant access to personal data and to refuse the rectification or\n" +
"deletion of your personal data.\n" +
"DECLARATION OF CONSENT\n" +
"I, hereby referred as User, confirm to have read and understood the above information on\n" +
"data protection. I consent to the processing of particularly sensitive personal data.\n" +
"The last change to this information was in March 2020.\n" +
"AiliA\n" +
"";
    
    public CommandTakamakaWebApp(String[] args,
            NotificationBroadcasterSupport nbs,
            long sequence) throws IOException {
        super(args, nbs, sequence);

        //FileHelper.initProjectFiles();
        opt = new Options();
        opt.addOption(Option
                .builder("s")
                .hasArg(false)
                .longOpt("start")
                .required(false)
                .desc("Start service")
                .build());
        opt.addOption(
                Option
                        .builder("u")
                        .hasArg(false)
                        .longOpt("update")
                        .required(false)
                        .desc("Update service")
                        .build());
        opt.addOption(
                Option
                        .builder("a")
                        .hasArg(false)
                        .longOpt("accept-eula")
                        .required(false)
                        .desc("Accept EULA to proceed")
                        .build());

        opt.addOption(
                Option
                        .builder("p")
                        .hasArg(false)
                        .longOpt("print-eula")
                        .required(false)
                        .desc("Print EULA License Agreement")
                        .build());

        par = new DefaultParser();
        try {
            cmd = par.parse(opt, args, true);
            List<String> argList = cmd.getArgList();
            if (!argList.isEmpty()) {
                throw new ParseException("not correct syntax written.");
            }
        } catch (ParseException ex) {
        }
    }

    @Override
    public void execute() {
        try {
            if (cmd.hasOption("a")) {
                startWebApp(cmd);
            } else if (cmd.hasOption("p")) {
                System.out.println(license);
            }else {
                Scanner sc = new Scanner(System.in);
                System.out.println(license);
                System.out.print("Do you accept EULA Terms Service? (Y/N)");
                if (sc.nextLine().equals("Y")) {
                    startWebApp(cmd);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(CommandTakamakaWebApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startWebApp(CommandLine cmd) throws Exception {
        initCommandVariables();
        checkAndDownloadFiles(cmd);
        startSecondJVM();
    }

    public void startSecondJVM() throws Exception {
        Path defaultApplicationDirectoryPath = FileHelper.getDefaultApplicationDirectoryPath();
        Path jdkPath = Paths.get(defaultApplicationDirectoryPath.toString(), "jdk", "jdk-" + suffix + "-" + arch + "_bin", "bin", "java");

        File file = new File(defaultApplicationDirectoryPath.toString() + "/jdk/jdk-" + suffix + "-" + arch + "_bin/bin/java");

        if (file.exists()) {
            System.out.println("Is Execute allow : " + file.canExecute());
            System.out.println("Is Write allow : " + file.canWrite());
            System.out.println("Is Read allow : " + file.canRead());
        }

        file.setExecutable(true);

        Path warPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "walletweb-1.0-SNAPSHOT.war");
        Path payaraPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "payara-micro-5.2020.7.jar");

        Runtime runtime = Runtime.getRuntime();

        String[] arr = new String[]{jdkPath.toString(), "-jar", payaraPath.toString(), "--rootDir", "/tmp/py", "--deploy", warPath.toString()};
        System.out.println(arr[0]);

        Process exec = runtime.exec(arr);

        new Thread(new SyncPipe(exec.getErrorStream(), System.out)).start();
        new Thread(new SyncPipe(exec.getInputStream(), System.out)).start();

    }

    private void downloadResource(String resourceName, Path destinationPath) throws MalformedURLException, IOException {
        try {
            URL url = new URL(resourceName);
            HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
            long completeFileSize = httpConnection.getContentLength();

            try ( java.io.BufferedInputStream resourceAsStream = new java.io.BufferedInputStream(httpConnection.getInputStream())) {
                Path resourcePath = Paths.get(destinationPath.toString());
                System.out.println(resourcePath.toFile().toString());

                File file = new File(resourcePath.toFile().toString());
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);

                java.io.FileOutputStream fos = new java.io.FileOutputStream(
                        resourcePath.toFile().toString());
                try ( java.io.BufferedOutputStream bout = new BufferedOutputStream(
                        fos, 1024)) {
                    byte[] data = new byte[1024];
                    long downloadedFileSize = 0;
                    int x;
                    while ((x = resourceAsStream.read(data, 0, 1024)) >= 0) {
                        downloadedFileSize += x;

                        bout.write(data, 0, x);
                    }
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    class SyncPipe implements Runnable {

        public SyncPipe(InputStream istrm, OutputStream ostrm) {
            istrm_ = istrm;
            ostrm_ = ostrm;
        }

        public void run() {
            try {
                final byte[] buffer = new byte[1024];
                for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
                    ostrm_.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private final OutputStream ostrm_;
        private final InputStream istrm_;
    }

    public void checkAndDownloadFiles(CommandLine cmd) throws Exception {
        String jsonUrl = "https://downloads.takamaka.dev/integrityCheckSumReleasesWebApp.json";

        String data = "";
        try {
            URL urlObject = new URL(jsonUrl);
            URLConnection urlConnection = urlObject.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            data = FileHelper.readFromInputStream(inputStream);
        } catch (IOException ex) {

        }

        jsonIntegrity = new JSONObject(data);
        System.out.println(jsonIntegrity.toString());

        Path defaultApplicationDirectoryPath = FileHelper.getDefaultApplicationDirectoryPath();

        libraryJdkPath = Paths.get(defaultApplicationDirectoryPath.toString(), "jdk", "jdk-" + suffix + "-" + arch + "_bin.zip");
        jdkHash = libraryJdkPath.toFile().exists() ? FileHelper.hash256(FileUtils.readFileToByteArray(libraryJdkPath.toFile())) : "";

        if (!libraryJdkPath.toFile().exists()
                || (cmd.hasOption("u") && !jdkHash.equals(jsonIntegrity.getJSONObject("jdk").getJSONObject(suffix).getJSONObject(arch).get("hash").toString()))) {
            downloadResource(
                    jsonIntegrity.getJSONObject("jdk").getJSONObject(suffix).getJSONObject(arch).get("url").toString(),
                    Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "jdk", "jdk-" + suffix + "-" + arch + "_bin.zip")
            );
        }
        IgniteUtils.unzip(Paths.get(jdkPath.toString(), "jdk-" + suffix + "-" + arch + "_bin.zip").toFile(), jdkPath.toFile(), null);

        libraryPayaraPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "payara-micro-5.2020.7.jar");
        payaraHash = libraryPayaraPath.toFile().exists() ? FileHelper.hash256(FileUtils.readFileToByteArray(libraryPayaraPath.toFile())) : "";

        if (!libraryPayaraPath.toFile().exists()
                || (cmd.hasOption("u") && !payaraHash.equals(jsonIntegrity.getJSONObject("payara").get("hash").toString()))) {
            downloadResource(
                    jsonIntegrity.getJSONObject("payara").get("url").toString(),
                    Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "webapp", "payara-micro-5.2020.7.jar")
            );
        }

        libraryWebAppPath = Paths.get(defaultApplicationDirectoryPath.toString(), "webapp", "walletweb-1.0-SNAPSHOT.war");
        webAppHash = libraryWebAppPath.toFile().exists() ? FileHelper.hash256(FileUtils.readFileToByteArray(libraryWebAppPath.toFile())) : "";

        System.out.println(webAppHash);
        System.out.println("remote webapp hash: " + jsonIntegrity.getJSONObject("webapp").get("hash").toString());

        if (!libraryWebAppPath.toFile().exists()
                || (cmd.hasOption("u") && !webAppHash.equals(jsonIntegrity.getJSONObject("webapp").get("hash").toString()))) {
            downloadResource(
                    jsonIntegrity.getJSONObject("webapp").get("url").toString(),
                    Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "webapp", "walletweb-1.0-SNAPSHOT.war")
            );
        }
    }

    public void initCommandVariables() throws Exception {
        switch (System.getProperty("os.name")) {
            case "Mac OS X":
                suffix = "mac";
                break;
            case "Linux":
                suffix = "linux";
                break;
            default:
                suffix = "windows";
                break;
        }

        boolean is64bit;
        if (suffix.equals("windows")) {
            is64bit = (System.getenv("ProgramFiles(x86)") != null);
        } else {
            is64bit = (System.getProperty("os.arch").contains("64"));
        }

        if (is64bit) {
            arch = "x64";
        }

        webapp = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "webapp");
        jdkPath = Paths.get(FileHelper.getDefaultApplicationDirectoryPath().toString(), "jdk");

        try {
            FileHelper.initProjectFiles();
            //FileHelper.deleteFolderContent(jdkPath.toFile());
            if (!FileHelper.directoryExists(jdkPath)) {
                FileHelper.createDir(jdkPath);
            }
            //FileHelper.deleteFolderContent(webapp.toFile());
            if (!FileHelper.directoryExists(webapp)) {
                FileHelper.createDir(webapp);
            }

        } catch (IOException ex) {
            Logger.getLogger(CommandTakamakaWebApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
