import React, {useCallback, useRef, useState} from 'react';
import {Link} from 'react-router-dom';
import Form, {ButtonItem, ButtonOptions, Item, Label, RequiredRule} from 'devextreme-react/form';
import LoadIndicator from 'devextreme-react/load-indicator';
import {useAuth} from '../../contexts/auth';
import './login-form.scss';

export default function (props) {
    const {logIn} = useAuth();
    const [loading, setLoading] = useState(false);
    const formData = useRef({userId: 'admin', password: 'Secret_123'});

    const onSubmit = useCallback(async (e) => {
        e.preventDefault();
        const {userId, password} = formData.current;
        setLoading(true);
        await logIn(userId, password);
        setLoading(false);
    }, [logIn]);

    return (
        <form className={'login-form'} onSubmit={onSubmit}>
            <Form formData={formData.current} disabled={loading}>
                <Item
                    dataField={'userId'}
                    editorType={'dxTextBox'}
                    editorOptions={userIdEditorOptions}>
                    <RequiredRule message="UserId is required"/>
                    <Label visible={false}/>
                </Item>
                <Item
                    dataField={'password'}
                    editorType={'dxTextBox'}
                    editorOptions={passwordEditorOptions}>
                    <RequiredRule message="Password is required"/>
                    <Label visible={false}/>
                </Item>
                <Item
                    dataField={'rememberMe'}
                    editorType={'dxCheckBox'}
                    editorOptions={rememberMeEditorOptions}>
                    <Label visible={false}/>
                </Item>
                <ButtonItem>
                    <ButtonOptions
                        width={'100%'}
                        type={'default'}
                        useSubmitBehavior={true}>
            <span className="dx-button-text">
              {
                  loading ? <LoadIndicator width={'24px'} height={'24px'} visible={true}/> : 'Login'
              }
            </span>
                    </ButtonOptions>
                </ButtonItem>
                <Item>
                    <div className={'link'}>
                        <Link to={'/reset-password'}>Forgot password?</Link>
                    </div>
                </Item>
            </Form>
        </form>
    );
}

const userIdEditorOptions = {stylingMode: 'filled', placeholder: 'UserId', mode: 'text'};
const passwordEditorOptions = {stylingMode: 'filled', placeholder: 'Password', mode: 'password'};
const rememberMeEditorOptions = {text: 'Remember me', elementAttr: {class: 'form-text'}};
