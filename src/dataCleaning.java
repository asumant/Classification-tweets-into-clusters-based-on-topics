import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import Jama.*;

public class dataCleaning {
	private BufferedWriter out;
	private HashMap<String,Integer> unique_words;
	private ArrayList<wordArray> frequent_words;
	private ArrayList<String> words;
	private int max_frequency;
	private static final String[] stopwords = {":-\\(",":-\\)","\\by[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bbro[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bur[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\blol[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",":\\(","=\\)","\\.",":p",":\\)","\\bi\\b","\\b@[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\?","\\!","\\bhttp[:/\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\b[\\d\\?\\!\\#\\~\\@\\$]*\\b","\\ball[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\ba[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\babout[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\babove[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bacross[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bafter[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bafterwards[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bagain[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\balmost[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\balone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\balong[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\balready[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\balso[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\balthough[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\balways[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bam[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bamong[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bamongst[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bamoungst[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bamount[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",  "\\ban[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\band[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\banother[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bany[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\banyhow[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\banyone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\banything[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\banyway[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\banywhere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bare[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\baround[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bas[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",  "\\bat[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bback[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bbe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bbecame[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbecause[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bbecome[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
       "\\bbecomes[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbecoming[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbeen[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbefore[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbeforehand[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbehind[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbeing[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bbelow[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbeside[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbesides[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbetween[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbeyond[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbill[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bboth[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bbottom[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
       "\\bbut[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bby[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bcall[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bcan[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bcannot[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bcant[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bco[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bcon[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bcould[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bcouldnt[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bde[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bdescribe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bdetail[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bdo[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bdone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bdown[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bdue[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bduring[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beach[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beg[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beight[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beither[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beleven[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\belse[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\belsewhere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bempty[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\benough[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\betc[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beven[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bever[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bevery[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beveryone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\beverything[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\beverywhere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bexcept[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfew[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfifteen[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfify[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfill[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfind[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bfire[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfirst[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfive[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfor[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bformer[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bformerly[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bforty[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfound[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bfour[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfrom[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfront[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfull[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bfurther[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bget[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bgive[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bgo[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhad[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bhas[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhasnt[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhave[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhence[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bher[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhereafter[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bhereby[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bherein[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhereupon[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhers[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bherself[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhim[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhimself[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bhis[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhow[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhowever[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bhundred[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bie[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bif[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bin[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\binc[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bindeed[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\binterest[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\binto[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bis[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bit[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bits[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bit's[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bitself[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bkeep[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\blast[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\blatter[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\blatterly[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bleast[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bless[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bltd[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmade[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmany[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bmay[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bme[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmeanwhile[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmight[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmill[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmine[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmore[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmoreover[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bmost[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmostly[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmove[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmuch[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmust[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmy[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bmyself[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bname[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bnamely[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bneither[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnever[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnevertheless[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnext[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnine[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bno[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bnobody[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnoone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnor[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnot[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnothing[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnow[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bnowhere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bof[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\boff[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\boften[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bon[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bonce[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bonly[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bonto[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bor[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bother[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bothers[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\botherwise[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bour[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bours[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bourselves[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bout[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bover[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bown[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bpart[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bper[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bperhaps[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bplease[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bput[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\brather[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bre[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsame[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsee[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bseem[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bseemed[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bseeming[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bseems[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bserious[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bseveral[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bshe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bshould[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bshow[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bside[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bsince[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsincere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsix[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsixty[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bso[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsome[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsomehow[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bsomeone[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsomething[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsometime[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsometimes[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsomewhere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bstill[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bsuch[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bsystem[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btake[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bten[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthan[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthat[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\btheir[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthem[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthemselves[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthen[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthence[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthereafter[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bthereby[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btherefore[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btherein[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthereupon[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthese[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthey[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthickv[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bthin[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthird[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthis[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthose[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthough[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthree[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthrough[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bthroughout[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthru[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bthus[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bto[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btogether[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btoo[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btop[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\btoward[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btowards[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btwelve[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btwenty[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\btwo[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bun[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bunder[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\buntil[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bup[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bupon[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bus[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bvery[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bvia[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwas[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bwell[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhat[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhatever[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhen[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhence[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhenever[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bwhere[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhereafter[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhereas[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhereby[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwherein[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhereupon[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", 
       "\\bwherever[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhether[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhich[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhile[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhither[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwho[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhoever[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bwhole[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhom[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhose[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwhy[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwill[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwith[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwithin[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bwithout[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bwould[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\byet[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\byou[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\byour[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\byours[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\byourself[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\byourselves[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b",
        "\\bthe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b", "\\bdid[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\bthe[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\b's[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","-",":","\\b'll[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\b'm[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","\\b't[\\w\\d\\.\\?\\!\\#\\~\\@\\$]*\\b","'","\\b[A-Za-z]\\b","/","@","\\(","\\)","\\bll[\\d\\?\\!\\#\\~\\@\\$]*\\b",";"};
	
	File f;
	FileWriter clean_file;
	FileWriter docTermCsv;
	
	
	public dataCleaning()
	{
		words = new ArrayList<String>();
		
		
	}
	public void chop_words()
	{
		f = new File("tweet_test.csv");
		FileInputStream fin;
		try {
			fin = new FileInputStream(f);
		
        DataInputStream din = new DataInputStream(fin);
        BufferedReader bin = new BufferedReader(new InputStreamReader(din));
		
		String line;
		StringTokenizer st;
		unique_words = new HashMap<String,Integer>();
		frequent_words = new ArrayList<wordArray>();
		int k = 0;
		
		max_frequency = 0;
		try {
			clean_file = new FileWriter("tweet_test_clean.csv");
			out = new BufferedWriter(clean_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(k<99981)
		{
			line = bin.readLine();
			
			line = line.toLowerCase();
			if(line.equalsIgnoreCase("")){
				k++;
				continue;
			}
			for (int i=0; i< stopwords.length ; i++)
			{
				Pattern p = Pattern.compile(stopwords[i]);
				Matcher m = p.matcher(line);
				line = m.replaceAll("");
			}
			st = new StringTokenizer(line);
			wordArray token1 = new wordArray();
			String token;
			while(st.hasMoreTokens())
			{
				token = st.nextToken();
				token1 = null;
				token1 = new wordArray();
				if(unique_words.get(token) == null)	{
				
					unique_words.put(token,1);
					words.add(token);
					if(max_frequency<1)
						max_frequency = 1;
				}
				else	{
									
					int frequency = unique_words.get(token);
					
					unique_words.remove(token);
					
					if(max_frequency<frequency){
						max_frequency = frequency;
					}
					token1.word = token;
					token1.frequency = frequency+1;
					unique_words.put(token, frequency+1);
					
					if(frequent_words.size()==0)
						frequent_words.add(token1);
					else {
						int count = 0;
						int found = 0;
						
						while(count!=300|| (count < frequent_words.size()))
						{
							wordArray term = frequent_words.get(count);
							if(term.frequency<=token1.frequency)	
								{
									found = 1;
									break;
								}
							count++;
						}
						if(found == 1)	{
							
							for(int m =0 ; m<frequent_words.size() ; m++)	{
								if(frequent_words.get(m).word.equalsIgnoreCase(token1.word))
									frequent_words.remove(m);
							}
							
							frequent_words.add(count, token1);
						}
					}
				}
			}
			System.out.println(k);
			k++;
			
			out.write(line+"\n");
		}
		System.out.println("Max Freq: "+max_frequency);
		System.out.println("total number of unique words"+unique_words.size());
		System.out.println("top 300 frequent words are:");
		
		for(int j=0 ; j<300 ; j++)
		{
			System.out.println("word: "+frequent_words.get(j).word+" frequency: "+frequent_words.get(j).frequency);
			if(j>=frequent_words.size())
				break;	
		}
		out.close();
		try {
			clean_file = new FileWriter("unique_words.csv");
			out = new BufferedWriter(clean_file);
			for(int i=0;i<words.size();i++)
			{
				out.write(words.get(i)+"\n");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void create_doc_term_csv()
	{
		System.out.println("started doc term generation");
		
		try{
		f = new File("tweet_test_clean.csv");
		FileInputStream fin;
		
		fin = new FileInputStream(f);
		
        DataInputStream din = new DataInputStream(fin);
        BufferedReader bin = new BufferedReader(new InputStreamReader(din));
		
		String line;
		
		StringTokenizer st;
		int i = 0;
		
		double[][] term_count = new double[26573][300];
		
		for(int k=0;k<26573;k++)	{
			for(int j=0;j<300;j++)
				term_count[k][j] = new Double(0);
		}
		
		while(i<26573)
		{	
			line = bin.readLine();	
			if(line == null){
				i++;
				continue;
			}
			line = 	line.toLowerCase();
			String token;
			st = new StringTokenizer(line);
			while(st.hasMoreTokens())
			{
				token = st.nextToken();
				for(int j = 0 ; j<300 ; j++)
				{
					if(token.equalsIgnoreCase(frequent_words.get(j).word))
					{
						term_count[i][j] = term_count[i][j]+1;
						break;
					}
				}
			}
			
			i++;
			System.out.println(i);
		}
		System.out.println("creating doc term csv");
			try {
				docTermCsv = new FileWriter("doc_term.csv");
				out = new BufferedWriter(docTermCsv);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int b=0;b<26573;b++)	{
				for(int j=0; j<300 ; j++){
					out.write(Double.toString(term_count[b][j]));
					if(j!=299)
						out.write(",");	
				}
				out.write("\n");
			}
			out.close();
			System.out.println("Computing svd...");
			Matrix m = new Matrix(term_count);
			SingularValueDecomposition svd = new SingularValueDecomposition(m);
			Matrix v = svd.getV();
			Matrix s = svd.getS();
			double [][] vmat = v.getArray();
			double [][] smat = s.getArray();
			
			System.out.println("creating csv files for S V");
			FileWriter sfile;
			BufferedWriter sout=null;
			try {
				docTermCsv = new FileWriter("svdV.csv");
				sfile = new FileWriter("svdS.csv");
				out = new BufferedWriter(docTermCsv);
				sout = new BufferedWriter(sfile);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Writing V...");
			for(int a=0; a<v.getRowDimension();a++){
				for(int b=0;b<v.getColumnDimension();b++)	{
					out.write(Double.toString(vmat[a][b]));
					if(b!=299)
						out.write(",");	
				}
				out.write("\n");
			}
			out.close();
	
			System.out.println("Writing S..");
			for(int a=0; a<s.getRowDimension();a++){
				for(int b=0;b<s.getColumnDimension();b++)	{
					sout.write(Double.toString(smat[a][b]));
					if(b!=299)
						sout.write(",");	
				}
				sout.write("\n");
			}
			sout.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		preprocess p = new preprocess("test");
		p.process_dataset_directory();
		dataCleaning d = new dataCleaning();
		d.chop_words();
		d.create_doc_term_csv();
	}
	
}