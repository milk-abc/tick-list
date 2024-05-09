import React from "react";
import type { FormProps } from "antd";
import { Button, Checkbox, Form, Input } from "antd";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { LockOutlined, UserOutlined } from "@ant-design/icons";
import "./index.less";

type FieldType = {
  username?: string;
  password?: string;
  remember?: string;
};

const onFinish: FormProps<FieldType>["onFinish"] = (values) => {
  console.log("Success:", values);
};

const onFinishFailed: FormProps<FieldType>["onFinishFailed"] = (errorInfo) => {
  console.log("Failed:", errorInfo);
};

const Login: React.FC = () => {
  const onFinish = (values: any) => {
    console.log("Received values of form: ", values);
  };

  return (
    <div className="loginContainer">
      <Form
        name="normal_login"
        className="login-form"
        initialValues={{ remember: true }}
        onFinish={onFinish}
      >
        <Form.Item
          className="username"
          name="username"
          rules={[{ required: true, message: "请输入用户名!" }]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="用户名"
          />
        </Form.Item>
        <Form.Item
          className="password"
          name="password"
          rules={[{ required: true, message: "请输入密码!" }]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="密码"
          />
        </Form.Item>
        <Form.Item
          className="confirmCode"
          name="confirmCode"
          rules={[{ required: true, message: "请输入验证码!" }]}
        >
          <Input placeholder="验证码" />
        </Form.Item>
        <Form.Item>
          <div className="userAction">
            <a className="login-form-register" href="">
              注册
            </a>
            <a className="login-form-forgot" href="">
              忘记密码
            </a>
          </div>
        </Form.Item>

        <Form.Item>
          <Button
            type="primary"
            htmlType="submit"
            className="login-form-button"
          >
            登录
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default Login;
