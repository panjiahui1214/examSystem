package view;

import domain.Question;
import server.QuestionServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ExamView extends BaseView {
    private String title; // 窗口标题

    private int sumNum; // 总共题数
    private int nowNum = 1; // 当前题号
    private int passNum = 0; // 已答题数

    private ArrayList<String> titles; // 存放试卷题目
    private ArrayList<String> answers; // 存放试卷正确答案
    private HashMap<Integer, String> userAnswers = new HashMap<>(); // 存放用户的答案

    public ExamView(String title, int sumNum) {
        this.title = title;
        this.sumNum = sumNum;
        // 获取试题
        QuestionServer qs = new QuestionServer();
        HashMap<String, ArrayList> paper = qs.getPaper(sumNum);
        this.titles = paper.get("titles");
        this.answers = paper.get("answers");

        this.init(title);
    }

    private JPanel jPanel = new JPanel();
    private JTextArea mainText = new JTextArea();
    private JScrollPane jScrollPane = new JScrollPane(mainText);
    private JButton btnA = new JButton("A");
    private JButton btnB = new JButton("B");
    private JButton btnC = new JButton("C");
    private JButton btnD = new JButton("D");

    private JButton btnPrev = new JButton("上一题");
    private JButton btnSubmit = new JButton("交卷");
    private JButton btnNext = new JButton("下一题");
    private JLabel labelNowQ = new JLabel("当前题号：");
    private JLabel labelSumQ = new JLabel("总共题数：");
    private JLabel labelPassQ = new JLabel("已答题数：");
    private JLabel labelWaitQ = new JLabel("未答题数：");

    private JLabel nowQ = new JLabel(nowNum + "");
    private JLabel sumQ = new JLabel(sumNum + "");
    private JLabel passQ = new JLabel(passNum + "");
    private JLabel waitQ = new JLabel(sumNum - passNum + "");

    private JLabel labelLeftTime = new JLabel("剩余答题时间");
    private JLabel leftHour = new JLabel("01");
    private JLabel leftMin = new JLabel("00");
    private JLabel leftSec = new JLabel("00");
    private JLabel labelColon1 = new JLabel("：");
    private JLabel labelColon2 = new JLabel("：");

    private Font bigFont = new Font("黑体", Font.LAYOUT_LEFT_TO_RIGHT, 20);

    @Override
    protected void setElement() {
        jPanel.setLayout(null);
        jScrollPane.setBounds(20, 20, 550, 330);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 设置不产生水平滚动条
        mainText.setEnabled(false);
        mainText.setLineWrap(true); // 设置自动换行
        mainText.setWrapStyleWord(true); // 设置换行不断字
        mainText.setFont(new Font("黑体", Font.BOLD, 23));

        btnA.setBounds(60, 370, 80, 30);
        btnB.setBounds(190, 370, 80, 30);
        btnC.setBounds(310, 370, 80, 30);
        btnD.setBounds(430, 370, 80, 30);
        btnPrev.setBounds(60, 420, 80, 30);
        btnSubmit.setBounds(250, 420, 80, 30);
        btnSubmit.setForeground(Color.RED);
        btnNext.setBounds(430, 420, 80, 30);

        labelNowQ.setBounds(600, 60, 100, 30);
        labelNowQ.setFont(bigFont);
        labelSumQ.setBounds(600, 130, 100, 30);
        labelSumQ.setFont(bigFont);
        labelPassQ.setBounds(600, 200, 100, 30);
        labelPassQ.setFont(bigFont);
        labelWaitQ.setBounds(600, 270, 100, 30);
        labelWaitQ.setFont(bigFont);

        nowQ.setBounds(715, 60, 50, 30);
        setFontAndBlue(nowQ);
        sumQ.setBounds(715, 130, 50, 30);
        setFontAndBlue(sumQ);
        passQ.setBounds(715, 200, 50, 30);
        setFontAndBlue(passQ);
        waitQ.setBounds(715, 270, 50, 30);
        setFontAndBlue(waitQ);

        labelLeftTime.setBounds(605, 365, 120, 30);
        setFontAndRed(labelLeftTime);
        leftHour.setBounds(610, 400, 30, 30);
        setFontAndRed(leftHour);
        labelColon1.setBounds(640, 400, 20, 30);
        setFontAndRed(labelColon1);
        leftMin.setBounds(655, 400, 30, 30);
        setFontAndRed(leftMin);
        labelColon2.setBounds(685, 400, 20, 30);
        setFontAndRed(labelColon2);
        leftSec.setBounds(700, 400, 30, 30);
        setFontAndRed(leftSec);

        this.showQuestion();
    }

    protected void setFontAndBlue(JLabel jl) {
        jl.setForeground(Color.BLUE);
        jl.setFont(bigFont);
    }
    protected void setFontAndRed(JLabel jl) {
        jl.setForeground(Color.RED);
        jl.setFont(bigFont);
    }

    protected void showQuestion() {
        String title = titles.get(nowNum);
        mainText.setText("\n " + nowNum + "." + title.replace("<br>", "\n  "));
    }

    @Override
    protected void addElement() {
        jPanel.add(jScrollPane);
        jPanel.add(btnA);
        jPanel.add(btnB);
        jPanel.add(btnC);
        jPanel.add(btnD);
        jPanel.add(btnPrev);
        jPanel.add(btnSubmit);
        jPanel.add(btnNext);

        jPanel.add(labelNowQ);
        jPanel.add(labelSumQ);
        jPanel.add(labelPassQ);
        jPanel.add(labelWaitQ);

        jPanel.add(nowQ);
        jPanel.add(sumQ);
        jPanel.add(passQ);
        jPanel.add(waitQ);

        jPanel.add(labelLeftTime);
        jPanel.add(leftHour);
        jPanel.add(leftMin);
        jPanel.add(leftSec);
        jPanel.add(labelColon1);
        jPanel.add(labelColon2);

        this.add(jPanel);
    }

    @Override
    protected void addListener() {
        // 答案选项按钮的监听事件
        ActionListener answerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userAnswers.put(nowNum, ((JButton)e.getSource()).getText());
            }
        };
        btnA.addActionListener(answerListener);
        btnB.addActionListener(answerListener);
        btnC.addActionListener(answerListener);
        btnD.addActionListener(answerListener);
    }
}
