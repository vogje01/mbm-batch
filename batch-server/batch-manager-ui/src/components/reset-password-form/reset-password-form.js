import React, {useCallback, useRef, useState} from 'react';
import {Link, useHistory} from "react-router-dom";
import Form, {ButtonItem, ButtonOptions, Item, Label, RequiredRule} from 'devextreme-react/form';
import LoadIndicator from 'devextreme-react/load-indicator';
import notify from 'devextreme/ui/notify';
import './reset-password-form.scss'

const notificationText = 'We\'ve sent a link to reset your password. Check your inbox.';

export default function (props) {
  const history = useHistory();
  const [loading, setLoading] = useState(false);
  const formData = useRef({});

  const onSubmit = useCallback(async (e) => {
    e.preventDefault();
    const {userId} = formData.current;
    setLoading(true);

    fetch(process.env.REACT_MANAGER_API_URL + 'resetPassword/' + userId)
        .then((response) => {
          if (response.status === 200) {
            history.push('/login');
            notify(notificationText, 'success', 2500);
          }
        });

  }, [history]);

  return (
      <form className={'reset-password-form'} onSubmit={onSubmit}>
        <Form formData={formData.current} disabled={loading}>
          <Item
              dataField={'userId'}
              editorType={'dxTextBox'}
              editorOptions={UserIdEditorOptions}>
            <RequiredRule message="UserId is required"/>
            <Label visible={false}/>
          </Item>
          <ButtonItem>
            <ButtonOptions
                elementAttr={submitButtonAttributes}
                width={'100%'}
                type={'default'}
                useSubmitBehavior={true}>
            <span className="dx-button-text">
              {
                loading ? <LoadIndicator width={'24px'} height={'24px'} visible={true}/> : 'Reset my password'
              }
            </span>
            </ButtonOptions>
          </ButtonItem>
          <Item>
            <div className={'login-link'}>
              Return to <Link to={'/login'}>Sign In</Link>
            </div>
          </Item>
        </Form>
      </form>
  );
}

const UserIdEditorOptions = {stylingMode: 'filled', placeholder: 'UserId'};
const submitButtonAttributes = {class: 'submit-button'};
