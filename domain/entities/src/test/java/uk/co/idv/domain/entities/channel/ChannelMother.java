package uk.co.idv.domain.entities.channel;

public class ChannelMother {

    public static Channel fake() {
        return new FakeChannel();
    }

    private static class FakeChannel extends SimpleChannel {

        private FakeChannel() {
            super("fake-channel");
        }

    }

}
