import React, {useCallback, useRef, useState} from 'react';
import {useHistory, useParams} from 'react-router-dom';
import Form, {ButtonItem, ButtonOptions, CustomRule, Item, Label, RequiredRule,} from 'devextreme-react/form';
import LoadIndicator from 'devextreme-react/load-indicator';
import {errorMessage, infoMessage} from "../../utils/message-util";

const notificationText = 'Password has been changed.';

export default function (props) {
    const history = useHistory();
    const [loading, setLoading] = useState(false);
    const formData = useRef({});
    const {recoveryCode} = useParams();

    const onSubmit = useCallback(async (e) => {
        e.preventDefault();
        const {password} = formData.current;
        setLoading(true);

        fetch(process.env.REACT_APP_API_URL + 'changePassword/' + password + '/' + recoveryCode)
            .then((response) => {
                if (response.status === 200) {
                    infoMessage(notificationText);
                    history.push('/login');
                }
                if (response.status === 401) {
                    errorMessage("Password change request expo expired.")
                }
            });
    }, [history, recoveryCode]);

    const confirmPassword = useCallback(
        ({value}) => value === formData.current.password,
        []
    );

    const onCancel = useCallback(() => {
        history.push('/login');
    }, [history]);

    return (
        <form onSubmit={onSubmit}>
            <Form formData={formData.current} disabled={loading}>
                <Item
                    dataField={'password'}
                    editorType={'dxTextBox'}
                    editorOptions={passwordEditorOptions}>
                    <RequiredRule message="Password is required"/>
                    <Label visible={false}/>
                </Item>
                <Item
                    dataField={'confirmedPassword'}
                    editorType={'dxTextBox'}
                    editorOptions={confirmedPasswordEditorOptions}>
                    <RequiredRule message="Password is required"/>
                    <CustomRule
                        message={'Passwords do not match'}
                        validationCallback={confirmPassword}/>
                    <Label visible={false}/>
                </Item>
                <ButtonItem>
                    <ButtonOptions
                        width={'100%'}
                        type={'default'}
                        useSubmitBehavior={true}>
            <span className="dx-button-text">
              {
                  loading ? <LoadIndicator width={'24px'} height={'24px'} visible={true}/> : 'Continue'
              }
            </span>
                    </ButtonOptions>
                </ButtonItem>
                <ButtonItem>
                    <ButtonOptions
                        width={'100%'}
                        type={'text'}
                        useSubmitBehavior={false}
                        onClick={onCancel}>
                        Cancel
                    </ButtonOptions>
                </ButtonItem>
            </Form>
        </form>
    );
}

const passwordEditorOptions = {stylingMode: 'filled', placeholder: 'Password', mode: 'password'};
const confirmedPasswordEditorOptions = {stylingMode: 'filled', placeholder: 'Confirm Password', mode: 'password'};
