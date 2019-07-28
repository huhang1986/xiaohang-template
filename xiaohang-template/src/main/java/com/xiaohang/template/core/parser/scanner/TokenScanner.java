package com.xiaohang.template.core.parser.scanner;

import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.Token;


/**
 * 单个标签的扫描器
 * 
 * @author xiaohanghu
 * */
public interface TokenScanner {

	Token scanner(MnemonicReader mnemonicReader);

}
