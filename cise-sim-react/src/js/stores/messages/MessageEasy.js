export default class xmlMsgBody {
  body;
  acknowledge;
  checksum;

  constructor(xmlMsgBody) {
    this.body = xmlMsgBody;
    this.acknowledge = "";
    this.checksum = this.calcCheckSum(xmlMsgBody);
  }

  // or create a computed value if mutability is required
  calcCheckSum(s) {

    let hash = 0, strlen = s.length, i, c;
    if (strlen === 0) {
      return hash;
    }
    for (i = 0; i < strlen; i++) {
      c = s.charCodeAt(i);
      hash = ((hash << 5) - hash) + c;
      hash = hash & hash; // Convert to 32bit integer
    }
    return hash;
  }
}