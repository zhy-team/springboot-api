package com.payment.alipay.bill;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author: zhanghuiyu
 * @Description:支付宝对账工具类
 * @Date: create in 2020/12/23 13:24
 */
public class BillUtils {

    /**
     * 下载支付宝对账单
     *
     * @param urlStr   对账单下载地址
     * @param filePath 文件保存路径
     */
    public static void BillDeal(String urlStr, String filePath) {
        URL url = null;
        HttpURLConnection httpUrlConnection = null;
        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            url = new URL(urlStr);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(5 * 1000);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
            httpUrlConnection.connect();
            fis = httpUrlConnection.getInputStream();
            byte[] temp = new byte[1024];
            int b;
            fos = new FileOutputStream(new File(filePath));
            while ((b = fis.read(temp)) != -1) {
                fos.write(temp, 0, b);
                fos.flush();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
                if (httpUrlConnection != null)
                    httpUrlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static String readZipFile(String file, String encoding) {
        StringBuffer content=new StringBuffer();
        try {
            ZipFile zf = new ZipFile(file, Charset.forName("GBK"));
            for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
                ZipEntry ze = (ZipEntry) entries.nextElement();
                try {
                    if (ze.isDirectory()) {
                    } else {
                        System.err.println("file - " + ze.getName() + " : "
                                + ze.getSize() + " bytes");
                        long size = ze.getSize();
                        if (size > 0) {
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(
                                            zf.getInputStream(ze), encoding));
                            String line;
                            boolean flag = false;
                            // 支付宝交易号
                            int idxTranId = 0;
                            // 备注
                            int idxRemark = 0;
                            // 商户订单号
                            int idxPayOrderNo = 0;
                            // 订单金额
                            int idxRcvAmt = 0;
                            // 交易完成时间
                            int idxEndTime = 0;

                            while ((line = br.readLine()) != null) {
                                content.append(line);
                                if (line.equals("#支付宝业务明细查询")) {
                                    flag = true;
                                }
                                if (flag) {
                                    if (line.startsWith("#账号")) {
                                    }
                                    if (line.startsWith("#起始日期：")) {
                                    }

                                    if (line.startsWith("支付宝交易号")) {
                                        List<String> titles = Arrays
                                                .asList(line.split(","));
                                        idxTranId = titles.indexOf("支付宝交易号");
                                        idxPayOrderNo = titles.indexOf("商户订单号");
                                        int idxBankType = titles
                                                .indexOf("业务类型");
                                        int idxFeeType = titles.indexOf("商品名称");
                                        int idxTime = titles.indexOf("创建时间");
                                        idxEndTime = titles.indexOf("完成时间");
                                        int idxRefundNo = titles
                                                .indexOf("门店编号");
                                        int idxRefundFee = titles
                                                .indexOf("门店名称");
                                        int idxCouponFee = titles
                                                .indexOf("操作员");
                                        int idxPoundage = titles.indexOf("终端号");
                                        int idxCmsRate = titles.indexOf("对方账户");
                                        idxRcvAmt = titles.indexOf("订单金额（元）");
                                        int idxAppId = titles
                                                .indexOf("商家实收（元）");
                                        int idxShopNo = titles
                                                .indexOf("支付宝红包（元）");
                                        int idxSubShopNo = titles
                                                .indexOf("集分宝（元）");
                                        int idxCustId = titles
                                                .indexOf("支付宝优惠（元）");
                                        int idxTradeType = titles
                                                .indexOf("商家优惠（元）");
                                        int idxTradeStatus = titles
                                                .indexOf("券核销金额（元）");
                                        int idxRefundType = titles
                                                .indexOf("券名称");
                                        int idxRefundStatus = titles
                                                .indexOf("商家红包消费金额（元）");
                                        int idxSubject = titles
                                                .indexOf("卡消费金额（元）");
                                        int idxMerchantData = titles
                                                .indexOf("退款批次号/请求号");
                                        int idxRefundStatuss = titles
                                                .indexOf("服务费（元）");
                                        int idxSubjects = titles
                                                .indexOf("分润（元）");
                                        idxRemark = titles.indexOf("备注");
                                    }
                                    if (!line.startsWith("#")
                                            && !line.startsWith("支付宝交易号")) {
                                        String values[] = line.split(",");
                                        String payOrder = idxPayOrderNo > -1 ? values[idxPayOrderNo]
                                                : "";// 商户订单号
                                    }
                                    if (line.startsWith("#交易合计")) {

                                        String values[] = line.split("，");
                                        String DEAL_COUNT = values[0]
                                                .split("#交易合计：")[1].split("笔")[0];
                                        String SUCCESS_AMT = values[1]
                                                .split("商家实收共")[1].split("元")[0];
                                    }

                                }
                            }
                            br.close();

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }


    public static void createFile(String s, String path, String fileName) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(f, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeFileContent(path + fileName, s);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            appendMethodB(path + fileName, s);
        }
    }

    // 往文件里写
    public static boolean writeFileContent(String filepath, String newstr)
            throws IOException {
        Boolean bool = false;
        String filein = newstr;// 新写入的行，换行
        String temp = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);// 文件路径(包括文件名称)
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            // 文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            // 不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

    // 往文件里追加内容
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 往文件里追加内容
    public static void appendMethodB(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 删除文件
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = BillUtils.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = BillUtils.deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

}
