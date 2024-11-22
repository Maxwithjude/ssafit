// react_app/Chat.jsx
import SendbirdApp from '@sendbird/uikit-react/App'
import '@sendbird/uikit-react/dist/index.css'
import withSendbird from '@sendbird/uikit-react/withSendbird'
import sendbirdSelectors from '@sendbird/uikit-react/sendbirdSelectors'
const Chat = (props) => {
  const sdk = sendbirdSelectors.getSdk(props)
  const currentUser = sdk && sdk.currentUser && props.config.NICKNAME

  console.log(props.config.NICKNAME)
  if (currentUser) {
    const getTotalUnreadMessageCount = async () => {
      const startTime = performance.now() // 시작 시간 기록
      const unreadMessageCount =
        await sdk.groupChannel.getTotalUnreadMessageCount()
      const endTime = performance.now() // 종료 시간 기록

      sdk.currentUser.updateCurrentUserInfo({
        nickname: props.config.NICKNAME,
      })
      // 요청 시간 출력
      console.log(
        `Unread message count request took: ${endTime - startTime} ms`
      )

      props.setUnreadMessageCount(unreadMessageCount)
    }

    getTotalUnreadMessageCount()
    props.setSbUserInfo(currentUser)
  }

  return (
    <SendbirdApp
      appId={props.config.APP_ID}
      userId={props.config.USER_ID}
      nickname={props.config.NICKNAME}
    />
  )
}

const ChatWithSendbird = withSendbird(Chat)
export default ChatWithSendbird
