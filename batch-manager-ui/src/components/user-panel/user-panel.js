import React, {useMemo} from 'react';
import ContextMenu, {Position} from 'devextreme-react/context-menu';
import List from 'devextreme-react/list';
import {useAuth} from '../../contexts/auth';
import './user-panel.scss';

export default function ({menuMode}) {
  const {user, logOut} = useAuth();
  const menuItems = useMemo(() => ([
    {
      text: 'Profile',
      icon: 'user'
    },
    {
      text: 'Logout',
      icon: 'runner',
      onClick: logOut
    }
  ]), [logOut]);
  const backGrd = user.avatarUrl !== null ? `url(${user.avatarUrl}) no-repeat #fff` : `url('data:image/svg+xml;utf-8,<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18"><path d="M9 10c-2.33 0-7 1.17-7 3.5V16h14v-2.5c0-2.33-4.67-3.5-7-3.5zm5.29 4.29H3.71v-.79c.1-.49 2.59-1.79 5.29-1.79s5.19 1.3 5.29 1.79v.79zM9 9c2.22 0 4-1.79 4-4s-1.78-4-4-4C6.79 1 5.01 2.79 5.01 5S6.79 9 9 9zm0-6.29c1.26 0 2.28 1.03 2.28 2.29S10.26 7.29 9 7.29C7.75 7.29 6.72 6.26 6.72 5S7.75 2.71 9 2.71z"/></svg>'`;

  return (
      <div className={'user-panel'}>
        <div className={'user-info'}>
          <div className={'image-container'}>
            <div
                style={{background: backGrd, backgroundSize: 'cover'}}
                className={'user-image'}/>
          </div>
          <div className={'user-name'}>{user.userId}</div>
        </div>

        {menuMode === 'context' && (
            <ContextMenu
                items={menuItems}
                target={'.user-button'}
                showEvent={'dxclick'}
                width={210}
                cssClass={'user-menu'}>
              <Position my={'top center'} at={'bottom center'}/>
            </ContextMenu>
        )}
        {menuMode === 'list' && (
            <List className={'dx-toolbar-menu-action'} items={menuItems}/>
        )}
      </div>
  );
}
